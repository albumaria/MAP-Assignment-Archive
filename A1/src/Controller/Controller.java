package Controller;

import Model.IEntity;
import Repository.IRepo;
import Repository.RepoException;

public class Controller {
    private IRepo repo;

    public Controller(IRepo r) {
        repo = r;
    }

    public IEntity readEntity(int index) {
        try {
            return repo.getEntity(index);
        } catch (RepoException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void writeEntity(IEntity entity) {
        try {
            repo.addEntity(entity);
        } catch(RepoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeEntity(IEntity entity) {
        try {
            repo.removeEntity(entity);
        } catch(RepoException e) {
            System.out.println(e.getMessage());
        }
    }

    public IEntity[] olderThanOne() {
        IEntity[] copy = repo.getAll();
        IEntity[] olderArr = new IEntity[copy.length];
        int i = 0;

        for (IEntity e : copy) {
            if (e.Age() > 1) {
                olderArr[i] = e;
                i++;
            }
        }

        return olderArr;
    }
}
