import java.util.ArrayList;

public class cell {
    private int posX,posY;
    private int value;
    private int domainCount;
    private ArrayList<Integer> listOfDomain;
    private int unassignedDegree;
    private Boolean assigned;

//    public int setDomainCount(Board b, cell c)
//    {
//        int mask = getMaskOfDomain(b,c);
//        domainCount= b.countSetBits(mask);
//        return domainCount;
//    }


    public int getUnassignedDegree() {
        return unassignedDegree;
    }

    public void setUnassignedDegree(int unassignedDegree) {
        this.unassignedDegree = unassignedDegree;
    }

    public void setDomainCount(int value)
   {
       domainCount=value;
   }

    public int getDomainCount() {
        return domainCount;
    }

    public int getMaskOfDomain(Board board)
    {

        int posX= this.getPosX();
        int posY= this.getPosY();

       // System.out.println("rowmask"+ board.getRowmasks(posX));
        int domMask= board.getRowmasks(posX) & board.getColmasks(posY);

        return domMask;
    }

    public void  RemoveFromDomain(int value) {
        int index= listOfDomain.indexOf(value);
        listOfDomain.remove(index);

    }
    public void  addToDomain(int value) {
        listOfDomain.add(domainCount, value);

    }

    public ArrayList<Integer> getListOfDomain()
    {

       // ArrayList<Integer> domainList= new ArrayList<Integer>();

        return listOfDomain;

    }
    public void setListOfDomain(Board board)
    {
        int domMask= getMaskOfDomain(board);
        int i=1;
        while(domMask!=0)
        {
            domMask>>=1;
            if((domMask &1)!=0)
                listOfDomain.add(i);
            i++;


        }
    }


    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }
    public boolean getAssigned() {
        return this.assigned;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }


    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public cell(int x, int y , int value)
    {
        posX=x;
        posY=y;
        this.value=value;
        this.assigned=false;
        this.listOfDomain=new ArrayList<>();


    }

    public int getValue()
    {
        return value;
    }
}
