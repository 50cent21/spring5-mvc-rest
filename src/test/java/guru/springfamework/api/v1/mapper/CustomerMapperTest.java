package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;

public class CustomerMapperTest {

	public static final String FIRSTNAME = "Jimmy";
	public static final String LASTNAME = "Fallon";
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCustomerToCustomerDTO() throws Exception{
		
		//given
		Customer customer = new Customer();
		customer.setFirstName(FIRSTNAME);
		customer.setLastName(LASTNAME);
		
		//when
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
		
		//then
		assertEquals(FIRSTNAME, customerDTO.getFirstName());
		assertEquals(LASTNAME, customerDTO.getLastName());
	}

}
