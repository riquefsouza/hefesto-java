package br.com.hfs.service;

import br.com.hfs.dao.JobDAO;
import br.com.hfs.model.Job;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class JobService {

    @Inject
    private JobDAO jobDAO;

    public Optional<Job> findById( Job job){
        return jobDAO.findById(job);
    }

    public Optional<Job> findById( Long id){
        return jobDAO.findById(Job.build(id));
    }

    public List<Job> findAll(){
        return jobDAO.findAll();
    }

    public Job save (Job job){
        return jobDAO.save(job);
    }

}
