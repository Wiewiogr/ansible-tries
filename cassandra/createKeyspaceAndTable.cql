CREATE KEYSPACE loaddriver WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 2};  
CREATE TABLE loaddriver.test (  
    id uuid PRIMARY KEY,  
    x text,  
    y text,  
    );   
