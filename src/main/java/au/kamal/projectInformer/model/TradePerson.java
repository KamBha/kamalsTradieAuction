package au.kamal.projectInformer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trade_person")
public class TradePerson
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public TradePerson(String inName)
    {
        name = inName;
    }

    public TradePerson()
    {
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
