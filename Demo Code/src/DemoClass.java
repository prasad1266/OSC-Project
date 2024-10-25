public class DemoClass {
    int num;
    int abundance;
    int sum;

    public DemoClass(int num) {
        this.num = num;
    }

    public boolean getAbundance() {

        for (int i = num - 1; i >= 1; i--) {
            if (num % i == 0) {
                sum += i;
            }
            if(sum>num){
                abundance = sum-num;
                return true;}
        }
        return false;
    }

    public void getSum(){

        System.out.println("Number :" + num + " Sum :" + abundance + " Abundance :" + (abundance - num));
    }


    public static void main(String[] args) {
        int MAX = Integer.MAX_VALUE;

        int count = 0;
        for (int i = 2; i < MAX; i++) {

            DemoClass obj = new DemoClass(i);
            if (obj.getAbundance()) {
                count++;
                obj.getSum();
                if (count > 100)
                    break;
            }
        }
    }
}
