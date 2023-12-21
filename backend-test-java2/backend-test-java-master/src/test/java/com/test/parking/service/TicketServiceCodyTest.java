// No new imports needed 

@Test
public void testEntranceTicket_VehicleNotFound() {

  when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());
  
  Ticket result = ticketService.entranceTicket(1L);

  assertNull(result);
  verify(parkingSpaceService, never()).changeStatus(any(), anyInt());
}

@Test 
public void testEntranceTicket_NoSpaceAvailable() {

  Vehicle mockVehicle = new Vehicle();
  when(vehicleRepository.findById(1L)).thenReturn(Optional.of(mockVehicle));
  when(parkingSpaceService.availableSpace(any())).thenReturn(null);

  Ticket result = ticketService.entranceTicket(1L);

  assertNull(result);
  verify(parkingSpaceService, never()).changeStatus(any(), anyInt());
}

@Test
public void testExitTicket_TicketNotFound() {

  when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

  Ticket result = ticketService.exitTicket(1L);

  assertNull(result);
  verify(parkingSpaceService, never()).changeStatus(any(), anyInt());
}

@Test
public void testExitTicket_ParkingSpaceNotFound() {

  Ticket mockTicket = new Ticket();
  when(ticketRepository.findById(1L)).thenReturn(Optional.of(mockTicket));
  when(parkingSpaceRepository.findById(anyLong())).thenReturn(Optional.empty());

  Ticket result = ticketService.exitTicket(1L);

  assertNull(result.getExitTime());
  verify(parkingSpaceService, never()).changeStatus(any(), anyInt());  
}

@Test
public void testListTickets() {
  List<Ticket> mockTickets = Arrays.asList(new Ticket(), new Ticket());
  when(ticketRepository.findAll()).thenReturn(mockTickets);

  List<Ticket> result = ticketService.listTickets();

  assertEquals(mockTickets, result);
}
