#!/bin/sh

# Define variables
DB_NAME="ultraplex"
DB_USER="postgres"
DB_PASSWORD="password"
CONTAINER_NAME="ultraplex-postgres"

# Step 1: Start PostgreSQL Docker container
echo "Starting PostgreSQL container..."
docker run --name $CONTAINER_NAME -e POSTGRES_USER=$DB_USER -e POSTGRES_PASSWORD=$DB_PASSWORD -e POSTGRES_DB=$DB_NAME -p 5432:5432 -d postgres:latest

# Wait for PostgreSQL to start
echo "Waiting 10 seconds for PostgreSQL to initialize..."
sleep 10

echo "Database should be initialized successfully!"