package au.kamal.projectInformer.repository;

import au.kamal.projectInformer.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>
{
    List<Project> findByAuctionEnd(LocalDateTime inAuctionEnd);
}
