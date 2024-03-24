package org.tracker.animalstracker.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tracker.animalstracker.dto.ExceptionDto;
import org.tracker.animalstracker.dto.PetCountResult;
import org.tracker.animalstracker.dto.PetDto;
import org.tracker.animalstracker.service.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pet/tracker")
public class PetsTrackerController {

    private final PetService service;

    @PostMapping("/register-data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Registers tracker data for a pet",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PetDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)
            ))})
    public ResponseEntity<Optional<PetDto>> registerTrackerData(@Valid @RequestBody PetDto petDto) {
        return new ResponseEntity<>(service.createPets(petDto), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search and retrieved the pets based on the provided input",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PetDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)
            ))
    })
    public ResponseEntity<List<PetDto>> searchPets(@RequestBody PetDto petDto) {
        return new ResponseEntity<>(service.searchPets(petDto), HttpStatus.OK);
    }

    @GetMapping("/count-pet-trackType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pets counts base on group by Track and pet Type",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PetCountResult.class)))),
            @ApiResponse(responseCode = "404", description = "No pets found"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)
            ))
    })
    public ResponseEntity<List<PetCountResult>> countGroupByPetTypeAndTrack() {
        return new ResponseEntity<>(service.countGroupByPetTypeAndTrack(), HttpStatus.OK);
    }

}
