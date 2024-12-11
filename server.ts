import { APP_BASE_HREF } from '@angular/common';
import { CommonEngine } from '@angular/ssr';
import express from 'express';
import { fileURLToPath } from 'node:url';
import { dirname, join, resolve } from 'node:path';
import nodemailer from 'nodemailer';
import multer from 'multer';
import cors from 'cors'; 
import dotenv from 'dotenv'; 


dotenv.config();


const upload = multer({ dest: 'uploads/' });


const transporter = nodemailer.createTransport({
  service: 'gmail',  
  auth: {
    user: process.env['EMAIL_USER'],  
    pass: process.env['EMAIL_PASS']   
  }
});


export function app(): express.Express {
  const server = express();
  const serverDistFolder = dirname(fileURLToPath(import.meta.url));
  const browserDistFolder = resolve(serverDistFolder, '../browser');
  const indexHtml = join(serverDistFolder, 'index.server.html');

  const commonEngine = new CommonEngine();

  server.use(cors()); 
  server.use(express.json()); 

  server.set('view engine', 'html');
  server.set('views', browserDistFolder);

  

server.post('/send-email', upload.single('file'), (req, res) => {
  const { name, email, phone, date, time } = req.body;
  const file = req.file;

  
  if (!file) {
    res.status(400).send('No se ha subido ningún archivo.');
    return;  
  }

  const mailOptions = {
    from: process.env['EMAIL_USER'],
    to: 'ma.duenas@duocuc.cl',  
    subject: 'Confirmación de Transferencia',
    text: `Se ha recibido una nueva transferencia de ${name} (${email}, ${phone}) para la cita el ${date} a las ${time}.`,
    attachments: [
      {
        filename: file.originalname,
        path: file.path
      }
    ]
  };

  
  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      console.error('Error al enviar el correo:', error);
      res.status(500).send('Error al enviar el correo.');
      return;  
    }

    res.status(200).send('Comprobante enviado con éxito.');
    return;  
  });
});


  
  server.get('*.*', express.static(browserDistFolder, {
    maxAge: '1y'
  }));

  server.get('*', (req, res, next) => {
    const { protocol, originalUrl, baseUrl, headers } = req;

    commonEngine
      .render({
        documentFilePath: indexHtml,
        url: `${protocol}://${headers.host}${originalUrl}`,
        publicPath: browserDistFolder,
        providers: [{ provide: APP_BASE_HREF, useValue: baseUrl }],
      })
      .then((html) => res.send(html))
      .catch((err) => next(err));
  });

  return server;
}

function run(): void {
  const port = process.env['PORT'] || 3000;  

  
  const server = app();
  server.listen(port, () => {
  });
}

run();
