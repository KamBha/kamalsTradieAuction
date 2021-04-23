package au.kamal.projectInformer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer
{
    @GeneratedValue
    @Id
    private Long id;

    private String name;

    public Customer()
    {
    }

    public Customer(String inName)
    {
        name = inName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String inName)
    {
        name = inName;
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this).toString();
    }
}
