package Repository;

import Model.IEntity;

public class Repository implements IRepo{
    private IEntity[] array;
    private int index;

    public Repository(int cap) {
        index = 0;
        array = new IEntity[cap];
    }

    public void addEntity(IEntity e) throws RepoException {
        if (index == array.length)
            throw new RepoException("Array is full!");

        array[index] = e;
        index++;
    }

    public void removeEntity(IEntity e) throws RepoException {
        int i = 0;
        boolean found = false;

        for (i = 0; i < array.length; i++) {
            if (array[i].equals(e)) {
                found = true;

                for (int j=i ; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }

                i = array.length;
                this.index--;
            }
        }

        if (!found)
            throw new RepoException("Entity to remove was not found");
    }

    public IEntity getEntity(int i) throws RepoException {
        if (i < 0 || i >= index)
            throw new RepoException("Entity index given is out of bounds");

        return array[i];
    }

    public IEntity[] getAll() {
        IEntity[] copy = new IEntity[this.index];
        int i = 0;

        for (int j = 0 ; j < this.index ; j++) {
            copy[i] = this.array[j];
            i++;
        }

        return copy;
    }

}
