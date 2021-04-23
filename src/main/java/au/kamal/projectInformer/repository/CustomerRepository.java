package au.kamal.projectInformer.repository;

import au.kamal.projectInformer.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
}
