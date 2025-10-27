package rw.ac.ilpd.mis.shared.client.status;

import com.google.gson.Gson;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2025
 */
public class DatabaseStatus {
    private String database;
    private String connection;
    private String databaseTime;

    public DatabaseStatus() {
        this.database = "Uninitialized";
        this.connection = "Not Configured";
        this.databaseTime = "Not Attempted";
    }

    public DatabaseStatus(String database) {
        this();
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDatabaseTime() {
        return databaseTime;
    }

    public void setDatabaseTime(String databaseTime) {
        this.databaseTime = databaseTime;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "{" +
                "\"database\":" + (database == null ? "null" : "\"" + database + "\"") + ", " +
                "\"connection\":" + (connection == null ? "null" : "\"" + connection + "\"") + ", " +
                "\"databaseTime\":" + (databaseTime == null ? "null" : "\"" + databaseTime + "\"") +
                "}";
    }
}
