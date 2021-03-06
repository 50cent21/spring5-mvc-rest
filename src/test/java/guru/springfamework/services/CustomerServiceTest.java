package guru.springfamework.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.controllers.v1.CustomerController;
import guru.springfamework.repositories.CustomerRepository;

public class CustomerServiceTest {
	
	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;
	
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
		
	}

	@Test
	public void testGetAllCustomers() throws Exception{
		
		//given
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");
		
		Customer customer2 = new Customer();
		customer2.setId(1L);
		customer2.setFirstName("Michale");
		customer2.setLastName("Weston");
		
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
		
		//when
		List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
		
		assertEquals(2, customerDTOS.size());
	}
	
	@Test
	public void getCustomerById() throws Exception {
		
		//given
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");
		
		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));
		
		//when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);
		
		assertEquals("Michale", customerDTO.getFirstName());
	}
	
	@Test
	public void createNewCustomer() throws Exception {
		
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		//when
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
		
		//then
		assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}
	
	@Test
	public void saveCustomerByDTO() throws Exception {
		
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		//when
		CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);
		
		//then
		assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}
	
	@Test
	public void deleteCustomerById() throws Exception {
		
		Long id = 1L;
		
		customerRepository.deleteById(id);
		
		verify(customerRepository, times(1)).deleteById(anyLong());
	}

}
