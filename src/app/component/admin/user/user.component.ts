import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../../model/user.model';
import { UserService } from '../../../services/user-service/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RoleService } from '../../../services/role-service/role.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {
  public users$ = this.userService.getAll();
  public roles$ = this.roleService.getAll();
  public displayAddUserDialog = false;
  public addUserForm: FormGroup;
  public isEditMode = false;
  private currentUserId: number | null = null;

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private fb: FormBuilder
  ) {
    this.addUserForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      role: [{id:null}, Validators.required], // El rol se selecciona por ID
      status: [true],
    });
  }

  ngOnInit(): void {
    this.users$ = this.userService.getAll();
  }
  toggleUserStatus(user: User) {
    const updatedStatus = user.status; // Alternar el estado actual
    this.userService.updateStatus(user.id, updatedStatus).subscribe({
      next: () => {
        user.status = updatedStatus; // Actualiza el estado localmente
      },
      error: (err) => {
        console.error('Error al actualizar el estado del usuario:', err);
      },
    });
  }
  // Método para abrir el diálogo en modo creación
  showAddUserDialog() {
    this.isEditMode = false;
    this.currentUserId = null;
  
    // Restablecer el formulario con valores iniciales
    this.addUserForm.reset({
      name: '',
      email: '',
      phone: '',
      role: null, // Asegúrate de que el valor sea null si no hay rol seleccionado
      status: true,
    });
  
    this.displayAddUserDialog = true; // Mostrar el diálogo
  }

  // Método para abrir el diálogo en modo edición
  editUser(user: User) {
    this.isEditMode = true;
    this.currentUserId = user.id;
  
    this.addUserForm.setValue({
      name: user.name,
      email: user.email,
      phone: user.phone,
      role: user.role.id, // Asegúrate de que aquí sea solo el ID del rol
      status: user.status,
    });
  
    this.displayAddUserDialog = true;
  }
  
  delete(user: User) {
    this.userService.delete(user.id).subscribe({next: ()=>{
      this.users$ = this.userService.getAll();
    }});
  }

  onAddUser() {
    if (this.addUserForm.valid) {
      const formValue = this.addUserForm.value;
  
      if (!formValue.role) {
        alert('Por favor selecciona un rol válido.');
        return;
      }
  
      const userData = {
        ...formValue,
        role: { id: formValue.role }, 
      };
  
      if (this.isEditMode && this.currentUserId !== null) {
        this.userService.update(this.currentUserId, userData).subscribe({
          next: () => {
            this.hideAddUserDialog();
            this.users$ = this.userService.getAll(); 
          },
          error: (err) => console.error('Error al editar usuario:', err),
        });
      } else {
        this.userService.create(userData).subscribe({
          next: () => {
            this.hideAddUserDialog();
            this.users$ = this.userService.getAll();
          },
          error: (err) => console.error('Error al crear usuario:', err),
        });
      }
    }
  }
  

  hideAddUserDialog() {
    this.displayAddUserDialog = false;
  }
}

export interface UserForm{
  id:number;
  name: string;
  email: string;
  phone: string;
  role: {id:number};
  status: boolean;
}