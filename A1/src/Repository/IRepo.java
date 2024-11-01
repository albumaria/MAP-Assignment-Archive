package Repository;

import Model.IEntity;

public interface IRepo {
    public void addEntity(IEntity e) throws RepoException;
    public void removeEntity(IEntity e) throws RepoException;
    public IEntity getEntity(int i) throws RepoException;
    public IEntity[] getAll();
}
