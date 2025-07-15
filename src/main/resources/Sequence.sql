DROP TABLE IF EXISTS public.user_entity CASCADE;

CREATE EXTENSION IF NOT EXISTS postgres_fdw;

CREATE SERVER IF NOT EXISTS keycloak_server
FOREIGN DATA WRAPPER postgres_fdw
OPTIONS (host 'localhost', dbname 'keycloak', port '5432');

CREATE USER MAPPING IF NOT EXISTS FOR CURRENT_USER
SERVER keycloak_server
OPTIONS (user 'keycloak', password 'keycloak');

CREATE FOREIGN TABLE public.user_entity (
    id UUID,
    username VARCHAR
)
SERVER keycloak_server
OPTIONS (schema_name 'public', table_name 'user_entity');
