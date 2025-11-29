package org.example;

import org.example.entities.Cat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CatRepository extends ListCrudRepository<Cat, Integer> {
    Optional<Cat> findCatByName(String name);

    //JPQL-query
    @Query("""
        select cat from Cat cat
        where upper(cat.name) = upper(:name)
    """)
    Optional<Cat> findBy(@Param("name") String name);

    @Query("""
        select distinct cat from Cat cat
        left join fetch cat.favorites
        """)
    List<Cat> findCatsAndFood();

    @EntityGraph("Cat.favorites")
    List<Cat> findCatsBy();
}
