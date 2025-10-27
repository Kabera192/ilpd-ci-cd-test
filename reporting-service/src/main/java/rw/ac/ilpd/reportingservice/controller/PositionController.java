package rw.ac.ilpd.reportingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.ilpd.reportingservice.service.PositionService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionApi;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionResponse;
import java.util.List;
@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController implements PositionApi {
    private  final PositionService service;

    @Override
    public ResponseEntity<PositionResponse> createPosition(PositionRequest request) {
        PositionResponse response=service.createPosition(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<PositionResponse>> getAllPositions(String search) {
        List<PositionResponse> response=service.getAllPositions(search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PagedResponse<PositionResponse>> getPositionsWithPagination(String search,int page, int size, String sortBy, String sortDirection) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).ascending());
        PagedResponse<PositionResponse>response=service.getPositionsWithPagination(search,pageable);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PositionResponse> getPositionById(String id) {
        PositionResponse response=service.getPositionById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PositionResponse> updatePosition(String id, PositionRequest request) {
        PositionResponse response=service.updatePosition(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> deletePosition(String id) {
        String deletePosition = service.deletePosition(id);
        return ResponseEntity.ok(deletePosition);
    }

    @Override
    public ResponseEntity<PositionResponse> restoreArchive(String id) {
        PositionResponse response=service.restoreArchive(id);
        return ResponseEntity.ok(response);
    }
}
