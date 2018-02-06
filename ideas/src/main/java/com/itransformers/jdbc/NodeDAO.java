package com.itransformers.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by vasko on 06-02-2018.
 */
public class NodeDAO {
    private Connection connection;

    public NodeDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertNode(String nodeId, Date dt, int edges )  {
        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement("insert into node values (?, ?, ?)");
            insert.setString(1, nodeId);
            insert.setTimestamp(2, new Timestamp(dt.getTime()));
            insert.setInt(3, edges);
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
