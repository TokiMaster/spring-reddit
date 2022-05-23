package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swt.reddit.demo.dto.CommunityDTO;
import swt.reddit.demo.model.Community;
import swt.reddit.demo.service.CommunityService;
import swt.reddit.demo.service.serviceImpl.CommunityServiceImpl;
import swt.reddit.demo.service.serviceImpl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/communities")
public class CommunityController {

    @Autowired
    private CommunityServiceImpl CommunityService;

    @Autowired
    private CommunityService communityService;

    @GetMapping()
    public ResponseEntity<List<CommunityDTO>> getAllCommunities(){

        List<Community> communities = CommunityService.findAll();

        List<CommunityDTO> communitiesDTO = new ArrayList<>();
        for(Community community : communities){
            communitiesDTO.add(new CommunityDTO(community.getName(), community.getDescription(), community.getCreationDate()));
        }

        return new ResponseEntity<>(communitiesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCommunity(@PathVariable("id") Long id){
        Optional<Community> community = CommunityService.findCommunityById(id);
        if(community.isEmpty()){
            return ResponseEntity.badRequest().body("Community with given id doesn't exist");
        }
        CommunityDTO communityDTO = new CommunityDTO(community.get().getName(), community.get().getDescription(), community.get().getCreationDate());
        return new ResponseEntity<>(communityDTO, HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Transactional
    public ResponseEntity<?> createCommunity(@RequestBody @Valid CommunityDTO communityDTO, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        Community community = new Community(communityDTO.getName(), communityDTO.getDescription(), LocalDateTime.now());
        var createdCommunity = communityService.createCommunity(community);
        return ResponseEntity.ok(createdCommunity.getId());
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> editCommunity(@RequestBody @Valid CommunityDTO CommunityDTO, BindingResult result, @PathVariable("id") Long id){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        Optional<Community> community = CommunityService.findCommunityById(id);
        if (community.isPresent()){
            if(CommunityDTO.getDescription() != null){
                community.get().setDescription(CommunityDTO.getDescription());
            }
            if(CommunityDTO.getName() != null){
                community.get().setName(CommunityDTO.getName());
            }
        }else{
            return ResponseEntity.badRequest().body("Community with given id doesn't exist");
        }
        var updatedCommunity = CommunityService.updateCommunity(community.get());
        return ResponseEntity.ok(updatedCommunity.getId());
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> deleteCommunity(@PathVariable("id") Long id){
        Optional<Community> community = communityService.findCommunityById(id);
        if(community.isEmpty()){
            return ResponseEntity.badRequest().body("Community with given id doesn't exist");
        }
        CommunityService.deleteCommunity(community.get());
        return ResponseEntity.ok("Deleted community with id: " + id);
    }
}