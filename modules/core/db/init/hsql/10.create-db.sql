-- begin BPMSAMPLES_CONTRACT
create table BPMSAMPLES_CONTRACT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    DATE_ date,
    AMOUNT decimal(19, 2),
    --
    primary key (ID)
)^
-- end BPMSAMPLES_CONTRACT
-- begin BPMSAMPLES_TASK
create table BPMSAMPLES_TASK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CAPTION varchar(255) not null,
    PROCESS_STATE varchar(255),
    DESCRIPTION varchar(255),
    INITIATOR_ID varchar(36) not null,
    EXECUTOR_ID varchar(36) not null,
    ACCEPTANCE_REQUIRED boolean,
    --
    primary key (ID)
)^
-- end BPMSAMPLES_TASK
