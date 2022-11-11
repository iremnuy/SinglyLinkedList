import java.util.NoSuchElementException;

public class FactoryImpl implements Factory {

    private Holder first;
    private Holder last;
    private Integer size = 0;

    @Override
    public void addFirst(Product product) {
        if (first == null) {
            first = new Holder(null, product, null);
            size = 1;
            last = first;
            return;
        }
        Holder tmp = new Holder(null, product, this.first);
        // old first holder is now the next of the new first
        first.setPreviousHolder(tmp);
        first = tmp;
        size += 1;

    }

    @Override
    public void addLast(Product product) {
        findLast(first);
        if (first == null) {
            last = new Holder(null, product, null); // not sure if prev node must be null
            size = 1;
            first = last;
            return;
        }
        Holder tmp = new Holder(this.last, product, null);
        last.setNextHolder(tmp);
        last = tmp;
        size += 1;

    }

    @Override
    public Product removeFirst() throws NoSuchElementException { // this.first is null
        if (first == null) {
            throw new NoSuchElementException();
        }
        if (first.getNextHolder() == null) { // so if there is single element in the linked list
            int id = first.getProduct().getId();
            int value = first.getProduct().getValue();
            first = null;
            last = null;
            size = 0;
            Product old = new Product(id, value);
            return old;
        }
        int id = first.getProduct().getId();
        int value = first.getProduct().getValue();
        first.getNextHolder().setPreviousHolder(null);
        first = first.getNextHolder(); // if the size is 1 then next holder will be null and first will be null,prev
                                       // node is already null
        size -= 1; // every assignment decreases length by one
        Product oldFirst = new Product(id, value);
        return oldFirst;

    }

    @Override
    public Product removeLast() throws NoSuchElementException {

        if (first == null) {
            throw new NoSuchElementException();
        }
        Holder temp = first;
        while (temp.getNextHolder() != null) {
            temp = temp.getNextHolder();
        }
        Holder tempp = new Holder(null, temp.getProduct(), null);
        if (temp.getPreviousHolder() != null) {
            last = temp.getPreviousHolder();
            temp.getPreviousHolder().setNextHolder(null);

        } else {
            Holder tmp = new Holder(null, first.getProduct(), null);
            first = null;
            size = 0;
            return tmp.getProduct();
        }
        return tempp.getProduct();

    }

    @Override
    public Product find(int id) throws NoSuchElementException {
        Holder tmp = first;
        while (tmp != null && tmp.getProduct().getId() != id) {
            tmp = tmp.getNextHolder(); // if there is no such an element eventually getnextholder will be null at the
                                       // and node and tmp will be null,not sure if thic case is enough to throw the
                                       // error

        }
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        return tmp.getProduct();

    }

    @Override
    public Product update(int id, Integer value) throws NoSuchElementException {
        Holder tmp = first;
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        while (tmp.getProduct().getId() != id) {
            tmp = tmp.getNextHolder();
            if (tmp == null) {
                throw new NoSuchElementException();
            }

            // if there is no such an element eventually getnextholder will be null at the
            // and node and tmp will be null

        }
        int idd = tmp.getProduct().getId();
        int val = tmp.getProduct().getValue();
        tmp.getProduct().setValue(value);
        Product oldProduct = new Product(idd, val);

        return oldProduct;

    }

    @Override
    public Product get(int index) throws IndexOutOfBoundsException {
        int ind = 0;
        Holder temp = first;
        if (temp == null) {
            throw new IndexOutOfBoundsException();
        }
        while (ind != index) {
            temp = temp.getNextHolder();
            ind += 1;
            if (temp == null) {
                throw new IndexOutOfBoundsException();
            }

        }
        return temp.getProduct();
    }

    @Override
    public void add(int index, Product product) throws IndexOutOfBoundsException {
        if (index == 0) {
            addFirst(product);
        }
        int ind = 0;
        Holder temp = first;

        if (temp == null) { // if linked list is empty and index argument is not zero then index is out of
                            // bounds
            throw new IndexOutOfBoundsException();
        }

        while (ind != index) {

            if (temp == null) {
                throw new IndexOutOfBoundsException();
            }

            ind += 1;
            temp = temp.getNextHolder();

            // if index is larger than the index of last then temp will be null,if index is
            // negative then it will infinitely do it,ıdk if sth else is also necesarry to
            // throw an error

        }
        if (temp == null) {
            // if ind=index and temp is null this means add the product to the rightmost
            addLast(product);
        } else {
            Holder insrt = new Holder(temp.getPreviousHolder(), product, temp);
            temp.setPreviousHolder(insrt);
            if (insrt.getPreviousHolder() != null) {
                insrt.getPreviousHolder().setNextHolder(insrt);
            }
        }

        size += 1;

    }

    @Override
    public Product removeIndex(int index) throws IndexOutOfBoundsException { // reads RI and after a space index then
                                                                             // project1 class calls this method
        int ind = 0;
        Holder temp = first;
        if (temp == null) { // if the list is empty
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFirst();
        }
        while (ind != index) {

            ind += 1;
            temp = temp.getNextHolder();
            if (temp == null) {
                throw new IndexOutOfBoundsException();
            }

        }
        if (temp.getPreviousHolder() != null) {
            temp.getPreviousHolder().setNextHolder((temp.getNextHolder()));
        }
        if (temp.getNextHolder() != null) {
            temp.getNextHolder().setPreviousHolder(temp.getPreviousHolder());
        }
        size -= 1;
        return temp.getProduct();

    }

    @Override
    public Product removeProduct(int value) throws NoSuchElementException {
        Holder temp = first;
        if (temp == null) {
            throw new NoSuchElementException();
        }
        while (temp.getProduct().getValue() != value) {
            temp = temp.getNextHolder();
            if (temp == null) {
                throw new NoSuchElementException();
            }

        }
        if (temp.getPreviousHolder() != null) {
            temp.getPreviousHolder().setNextHolder((temp.getNextHolder()));
        } else {
            first = temp.getNextHolder();
            first.setPreviousHolder(null);
        }
        if (temp.getNextHolder() != null) {
            temp.getNextHolder().setPreviousHolder(temp.getPreviousHolder());
        } else {
            last = temp.getPreviousHolder();
            last.setNextHolder(null);
        }
        size -= 1;
        return temp.getProduct();

    }

    @Override
    public int filterDuplicates() {
        int dupNum = 0;
        Holder temp = first;

        while (temp != null) {
            Holder checkNext = temp.getNextHolder();
            while (checkNext != null) {
                if (temp.getProduct().getValue() == checkNext.getProduct().getValue()) {
                    // temp = checkNext;
                    if (checkNext.getPreviousHolder() != null) {
                        checkNext.getPreviousHolder().setNextHolder(checkNext.getNextHolder());
                    }
                    if (checkNext.getNextHolder() != null) {
                        checkNext.getNextHolder().setPreviousHolder(checkNext.getPreviousHolder());
                    }

                    // temp = null;
                    dupNum += 1;
                    size -= dupNum;

                }
                checkNext = checkNext.getNextHolder();

            }

            temp = temp.getNextHolder();
        }
        return dupNum;
    }

    @Override
    public void reverse() {
        findLast(first);

        Holder temp = null;
        Holder current = first;
        while (current != null) {
            temp = current.getPreviousHolder();
            current.setPreviousHolder(current.getNextHolder());
            current.setNextHolder(temp);
            current = current.getPreviousHolder();
        }
        while (temp != null) {
            first = temp;
            temp = temp.getPreviousHolder();
        }
        if (first != null) {
            first.setPreviousHolder(null);
        }
        Holder tempo = first;
        while (tempo != null && tempo.getNextHolder() != null) {
            tempo = tempo.getNextHolder();
        }
        last = tempo;

    }

    // Helper Methods

    public Holder getFirst() {
        return first;
    }

    /**
     * @return
     */
    public String printAll() { // null case check
        Holder temp = first;
        if (first == null) {
            return "{}";
        }

        String str = "{";
        while (temp.getNextHolder() != null) {
            str += temp.getProduct().toString();
            str += ",";
            temp = temp.getNextHolder();

        }
        str += temp.getProduct().toString();
        str += "}";
        return str;

    }

    public void findLast(Holder first) {
        Holder temp = first;
        if (temp != null) {
            while (temp.getNextHolder() != null) {
                temp = temp.getNextHolder();
            }
            last = temp;
            last.setNextHolder(null);
        }

    }

}