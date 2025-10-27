package rw.ac.ilpd.academicservice.handler;

import rw.ac.ilpd.sharedlibrary.dto.util.BulkResponse;

public class BulkResponseException extends RuntimeException
{
    private final BulkResponse<?> bulkResponse;

    public BulkResponseException(String message, BulkResponse<?> response)
    {
        super(message);
        bulkResponse = response;
    }

    public BulkResponse<?> getBulkResponse()
    {
        return bulkResponse;
    }
}
