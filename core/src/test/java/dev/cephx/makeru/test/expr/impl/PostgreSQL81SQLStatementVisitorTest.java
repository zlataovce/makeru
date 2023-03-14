package dev.cephx.makeru.test.expr.impl;

import dev.cephx.makeru.expr.ColumnSQLExpression;
import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.StatementFormattingStrategies;
import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.impl.PostgreSQL81SQLStatementVisitor;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostgreSQL81SQLStatementVisitorTest {
    @Test
    public void createTableUpperCase() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.UPPER_CASE);

        // CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders
        // (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id),
        // FOREIGN KEY (id) REFERENCES orders (customer_id), CHECK (char_length(name) > 3));
        v.visit(CreateTableSQLExpression.builder().tableName("customers").build());
        assertEquals("CREATE TABLE customers", v.toString());
        v.visitColumns();
        assertEquals("CREATE TABLE customers (", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("id").type("uuid").build());
        assertEquals("CREATE TABLE customers (id uuid", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL", v.toString());
        v.visitColumnConstraint(PrimaryKeyConstraintSQLExpression.builder().build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY", v.toString());
        v.visitColumnConstraint(DefaultConstraintSQLExpression.builder().expression("gen_random_uuid()").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid()", v.toString());
        v.visitColumnConstraint(ForeignKeyConstraintSQLExpression.builder().refTable("orders").refColumn("customer_id").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id)", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("name").type("text").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL", v.toString());
        v.visitColumnConstraint(UniqueConstraintSQLExpression.builder().build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE", v.toString());
        v.visitColumnConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3)", v.toString());
        v.visitTableConstraint(UniqueConstraintSQLExpression.builder().columnName("name").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name)", v.toString());
        v.visitTableConstraint(PrimaryKeyConstraintSQLExpression.builder().columnName("id").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id)", v.toString());
        v.visitTableConstraint(ForeignKeyConstraintSQLExpression.builder().columnName("id").refTable("orders").refColumn("customer_id").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES orders (customer_id)", v.toString());
        v.visitTableConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES orders (customer_id), CHECK (char_length(name) > 3)", v.toString());
        v.visitColumnsEnd();
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES orders (customer_id), CHECK (char_length(name) > 3))", v.toString());
        v.visitEnd();
        assertEquals("CREATE TABLE customers (id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid() REFERENCES orders (customer_id), name text NOT NULL UNIQUE CHECK (char_length(name) > 3), UNIQUE (name), PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES orders (customer_id), CHECK (char_length(name) > 3));", v.toString());
    }

    @Test
    public void createTableLowerCase() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.LOWER_CASE);

        // create table customers (id uuid not null primary key default gen_random_uuid() references orders
        // (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id),
        // foreign key (id) references orders (customer_id), check (char_length(name) > 3));
        v.visit(CreateTableSQLExpression.builder().tableName("customers").build());
        assertEquals("create table customers", v.toString());
        v.visitColumns();
        assertEquals("create table customers (", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("id").type("uuid").build());
        assertEquals("create table customers (id uuid", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null", v.toString());
        v.visitColumnConstraint(PrimaryKeyConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key", v.toString());
        v.visitColumnConstraint(DefaultConstraintSQLExpression.builder().expression("gen_random_uuid()").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid()", v.toString());
        v.visitColumnConstraint(ForeignKeyConstraintSQLExpression.builder().refTable("orders").refColumn("customer_id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id)", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("name").type("text").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null", v.toString());
        v.visitColumnConstraint(UniqueConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique", v.toString());
        v.visitColumnConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3)", v.toString());
        v.visitTableConstraint(UniqueConstraintSQLExpression.builder().columnName("name").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name)", v.toString());
        v.visitTableConstraint(PrimaryKeyConstraintSQLExpression.builder().columnName("id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id)", v.toString());
        v.visitTableConstraint(ForeignKeyConstraintSQLExpression.builder().columnName("id").refTable("orders").refColumn("customer_id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id)", v.toString());
        v.visitTableConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3)", v.toString());
        v.visitColumnsEnd();
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3))", v.toString());
        v.visitEnd();
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3));", v.toString());
    }

    @Test
    public void createTableMixedCase() {
        // this visitor implementation writes keywords in lower case by default
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        // create table customers (id uuid not null primary key default gen_random_uuid() references orders
        // (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id),
        // foreign key (id) references orders (customer_id), check (char_length(name) > 3));
        v.visit(CreateTableSQLExpression.builder().tableName("customers").build());
        assertEquals("create table customers", v.toString());
        v.visitColumns();
        assertEquals("create table customers (", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("id").type("uuid").build());
        assertEquals("create table customers (id uuid", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null", v.toString());
        v.visitColumnConstraint(PrimaryKeyConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key", v.toString());
        v.visitColumnConstraint(DefaultConstraintSQLExpression.builder().expression("gen_random_uuid()").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid()", v.toString());
        v.visitColumnConstraint(ForeignKeyConstraintSQLExpression.builder().refTable("orders").refColumn("customer_id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id)", v.toString());
        v.visitColumn(ColumnSQLExpression.builder().name("name").type("text").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text", v.toString());
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null", v.toString());
        v.visitColumnConstraint(UniqueConstraintSQLExpression.builder().build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique", v.toString());
        v.visitColumnConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3)", v.toString());
        v.visitTableConstraint(UniqueConstraintSQLExpression.builder().columnName("name").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name)", v.toString());
        v.visitTableConstraint(PrimaryKeyConstraintSQLExpression.builder().columnName("id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id)", v.toString());
        v.visitTableConstraint(ForeignKeyConstraintSQLExpression.builder().columnName("id").refTable("orders").refColumn("customer_id").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id)", v.toString());
        v.visitTableConstraint(CheckConstraintSQLExpression.builder().expression("char_length(name) > 3").build());
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3)", v.toString());
        v.visitColumnsEnd();
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3))", v.toString());
        v.visitEnd();
        assertEquals("create table customers (id uuid not null primary key default gen_random_uuid() references orders (customer_id), name text not null unique check (char_length(name) > 3), unique (name), primary key (id), foreign key (id) references orders (customer_id), check (char_length(name) > 3));", v.toString());
    }

    @Test
    public void dropTableUpperCase() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.UPPER_CASE);

        // DROP TABLE customers RESTRICT;
        v.visit(DropTableSQLExpression.builder().tableName("customers").action(DropTableSQLExpression.Action.RESTRICT).build());
        assertEquals("DROP TABLE customers RESTRICT", v.toString());
        v.visitEnd();
        assertEquals("DROP TABLE customers RESTRICT;", v.toString());
    }

    @Test
    public void dropTableLowerCase() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.LOWER_CASE);

        // drop table customers restrict;
        v.visit(DropTableSQLExpression.builder().tableName("customers").action(DropTableSQLExpression.Action.RESTRICT).build());
        assertEquals("drop table customers restrict", v.toString());
        v.visitEnd();
        assertEquals("drop table customers restrict;", v.toString());
    }

    @Test
    public void dropTableMixedCase() {
        // this visitor implementation writes keywords in lower case by default
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        // drop table customers restrict;
        v.visit(DropTableSQLExpression.builder().tableName("customers").action(DropTableSQLExpression.Action.RESTRICT).build());
        assertEquals("drop table customers restrict", v.toString());
        v.visitEnd();
        assertEquals("drop table customers restrict;", v.toString());
    }

    @Test
    public void tableConstraintUniqueNullsNotDistinct() {
        val v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertThrows(
                UnsupportedOperationException.class,
                () -> v.visitUniqueTableConstraint(UniqueConstraintSQLExpression.builder().nullsDistinct(false).build())
        );
    }

    @Test
    public void columnConstraintUniqueNullsNotDistinct() {
        val v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertThrows(
                UnsupportedOperationException.class,
                () -> v.visitUniqueColumnConstraint(UniqueConstraintSQLExpression.builder().nullsDistinct(false).build())
        );
    }

    @Test
    public void createTableIfNotExists() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertThrows(
                UnsupportedOperationException.class,
                () -> v.visit(CreateTableSQLExpression.builder().tableName("customers").ifNotExists(true).build())
        );
    }

    @Test
    public void dropTableIfExists() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertThrows(
                UnsupportedOperationException.class,
                () -> v.visit(DropTableSQLExpression.builder().ifExists(true).tableName("customers").build())
        );
    }

    @Test
    public void dropTableMultipleTables() {
        final SQLStatementVisitor v = new PostgreSQL81SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertDoesNotThrow(() -> v.visit(DropTableSQLExpression.builder().tableName("customers").tableName("customers2").build()));
    }
}
