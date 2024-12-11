import { ReservationStatusPipe } from './reservation-status.pipe';

describe('ReservationStatusPipe', () => {
  it('create an instance', () => {
    const pipe = new ReservationStatusPipe();
    expect(pipe).toBeTruthy();
  });
});
