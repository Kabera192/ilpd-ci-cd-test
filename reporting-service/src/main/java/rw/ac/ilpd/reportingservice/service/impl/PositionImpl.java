package rw.ac.ilpd.reportingservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.reportingservice.exception.EntityAlreadyExistsException;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.PositionMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.Position;
import rw.ac.ilpd.reportingservice.repository.nosql.PositionRepository;
import rw.ac.ilpd.reportingservice.service.PositionService;
import rw.ac.ilpd.reportingservice.service.UserPositionService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class PositionImpl implements PositionService {
    private  final PositionRepository repository;
    private  final UserPositionService userPositionServices;
    private final PositionMapper mapper;
    @Override
    public PositionResponse createPosition(@Valid PositionRequest request) {
        boolean existsByNameAndAbbreviation=repository.existsByNameAndAbbreviationAndIsDeleted(request.getName(),request.getAbbreviation(),false);
        if(existsByNameAndAbbreviation){
            log.info("Position already exists");
            throw new EntityAlreadyExistsException("Position already exists");
        }
        Position position=mapper.toPosition(request);
        Position result = repository.save(position);
        log.info("Position having Id: {} created ", result.getId());
        return mapper.fromPosition(result);
    }

    @Override
    public PositionResponse updatePosition(String id, PositionRequest request) {
        Position position=repository.findById(id).orElseThrow(()->new EntityNotFoundException("Position not found"));
        Position mappedPosition = mapper.toPositionUpdate(position,request);
        Position result = repository.save(mappedPosition);
        log.info("Position having Id: {} updated ", result.getId());
        return mapper.fromPosition(result);
    }

    @Override
    public PositionResponse getPositionById(String id) {
        Position position=getEntity(id).orElse(null);
        return mapper.fromPosition(position);
    }

    @Override
    public List<PositionResponse> getAllPositions(String search) {
        List<Position>positions=new ArrayList<>();
       if(search.isBlank()) {
            positions=repository.findAll();
        }else {
           positions=repository.findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCase(search, search);
       }

        return positions.stream().map(mapper::fromPosition).toList();
    }

    @Override
    public List<PositionResponse> getAllPositionByDeleteStatus(boolean isDeleted, String search) {
        List<Position>positions=new ArrayList<>();
        if(search.isBlank()) {
            positions=repository.findByIsDeleted(isDeleted);
        }else {
            positions=repository.findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCaseAndIsDeleted(search, search,isDeleted);
        }

        return positions.stream().map(mapper::fromPosition).toList();
    }

    @Override
    public PagedResponse<PositionResponse> getPositionsWithPagination(String search, Pageable pageable) {
        Page<Position>positions;
        if(search.isBlank()) {
          positions = repository.findAll(pageable);
        }else {
            positions=repository.findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCase(search, search,pageable);
        }
        List<PositionResponse>content=positions.stream().map(mapper::fromPosition).toList();
        return new PagedResponse<>(
                content,
                positions.getNumber(),
                positions.getSize(),
                positions.getTotalElements(),
                positions.getTotalPages(),
                positions.isLast()
        );
    }

    @Override
    public String deletePosition(String id) {
        Position position=getEntityAndDeleteStatus(id,false).orElseThrow(()->new EntityNotFoundException("Position not found"));
        boolean exist=userPositionServices.existByPositionId(position.getId());
        log.info("Position having Id: {} ", id);

        if(!exist){
            log.info("Position has not been used by any user i.e deletion of this position has to be hard delete");
            repository.deleteById(id);
            return "Position is deleted completely";
        }else {
            log.info("Position has  been used by any user i.e deletion of this position has to be soft");
            position.setDeleted(true);
            Position softDelete=repository.save(position);
            return "softDelete.getName()} has been archived successfully";
        }
    }

    @Override
    public PositionResponse restoreArchive(String id) {
        Position position=getEntity(id).orElseThrow(()->new EntityNotFoundException("Position not found"));
        boolean existInActive=position.isDeleted();
        if(!existInActive){
            throw new EntityAlreadyExistsException("Position is not archived to restore i.e Position is active");
        }
        position.setDeleted(false);
        Position result = repository.save(position);
        return mapper.fromPosition(result);
    }

    //    Helper
public Optional<Position> getEntity(String id){
    return repository.findById(id);
}
@Override
    public Optional<Position> getEntityAndDeleteStatus(String id,boolean isDeleted){
        return repository.findByIdAndIsDeleted(id,isDeleted);
    }
}
