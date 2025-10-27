package rw.ac.ilpd.sharedlibrary.dto.util;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class BulkResponse<T>
{
    private List<T> successful = new ArrayList<>();
    private List<ErrorDetail<T>> failed = new ArrayList<>();

    public List<T> getSuccessful()
    {
        return successful;
    }

    public void setSuccessful(List<T> successful)
    {
        this.successful = successful;
    }

    public void addSuccessful(T success)
    {
        this.successful.add(success);
    }

    public List<ErrorDetail<T>> getFailed()
    {
        return failed;
    }

    public void setFailed(List<ErrorDetail<T>> failed)
    {
        this.failed = failed;
    }

    public void addFailed(ErrorDetail<T> error)
    {
        this.failed.add(error);
    }
}