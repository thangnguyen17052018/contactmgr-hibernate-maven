package com.tma.contactmgr.model;

public class Connection {
    private String jdbcVendor;
    private String dbName;
    private String username;
    private String password;

    public Connection(String jdbcVendor, String dbName, String username, String password) {
        this.jdbcVendor = jdbcVendor;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public Connection(ConnectionBuilder builder) {
        this.jdbcVendor = builder.jdbcVendor;
        this.dbName = builder.dbName;
        this.username = builder.username;
        this.password = builder.password;
    }

    public static class ConnectionBuilder{
        private String jdbcVendor;
        private String dbName;
        private String username;
        private String password;

        public ConnectionBuilder(){}

        public ConnectionBuilder withJdbcVendor(String jdbcVendor) {
            this.jdbcVendor = jdbcVendor;
            return this;
        }

        public ConnectionBuilder withDb(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public ConnectionBuilder withUser(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public Connection build() {
            return new Connection(this);
        }
    }
}