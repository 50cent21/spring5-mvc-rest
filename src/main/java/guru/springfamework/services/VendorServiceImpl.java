package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.domain.controllers.v1.VendorController;
import guru.springfamework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;
	
	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public VendorListDTO getAllVendors() {
		
		List<VendorDTO> vendorDTOS = vendorRepository.findAll()
				.stream()
				.map( vendor -> {
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
					return vendorDTO;
				})
				.collect(Collectors.toList());
		
		return new VendorListDTO(vendorDTOS);
	}

	@Override
	public VendorDTO getVendorById(Long id) {
	    
		return vendorRepository.findById(id)
				.map(vendorMapper::vendorToVendorDTO)
				.map(vendorDTO -> {
					 vendorDTO.setVendorUrl(getVendorUrl(id));
					 return vendorDTO;
				})
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		
		return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
	}
	

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
		
		Vendor vendorToSave = vendorRepository.findById(id).get();
		vendorToSave.setId(id);
		
		return saveAndReturnDTO(vendorToSave);
	}


	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		
		return vendorRepository.findById(id)
				.map(vendor -> {
					if(vendorDTO.getName() != null) {
						vendor.setName(vendorDTO.getName());
					}
					
					VendorDTO vendorDTO2 = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO2.setVendorUrl(getVendorUrl(id));
					
					return vendorDTO2;
					
				}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		
		vendorRepository.deleteById(id);

	}
	
	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		
		Vendor savedVendor = vendorRepository.save(vendor);
		
		VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        
		returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
		
		return returnDTO;
	}
	
	private String getVendorUrl(Long id) {
		
		return VendorController.BASE_URL + "/" + id;
	}

}
