import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {

    System.out.println("******** Ejemplo Predicado y Funcion ***********");
    List<String> lista = Arrays.asList("uno", "dos");

    lista.forEach(s -> System.out.println(s));

    Predicate<String> pred = s -> s.length()>4;
    //hacemos la prueba contraria
    System.out.println(pred.negate().test("patata"));
    System.out.println(pred.test("patata"));
    System.out.println(pred.and(s->s.length()<8).test("patata"));

    Function<String,Integer> f1 = s -> s.length();
    System.out.println(
    f1.andThen(n -> n+1).apply("patata"));

    System.out.println("******** Ejemplo Supplier no devuelve ***********");

    String cadena ="aguacate";

    //supplier no tiene argumentos de entrada
    Supplier<String> supp = () -> cadena.toUpperCase();
    System.out.println(supp.get());

    System.out.println("******** Ejemplo Consumer ***********");

    Consumer<String> c1 = s -> System.out.println(s);
    //object::method
    Consumer<String> c2 = System.out::println;
    c2.accept("patata");
    c1.accept("aguacate");

    //es lo mismo
    IntBinaryOperator op1 = (a,b) ->  Math.max(a,b);
    //class::static method
    IntBinaryOperator op2 = Math::max;

    System.out.println(op1.applyAsInt(2,4));

    Function<String,Integer> fu1 = s -> s.length();
    Function<String,Integer> fu2 = String::length;

    Supplier sup = () -> new String();
    Supplier sup2 = String::new;
    Supplier sup3 = () -> new String("patata");
    System.out.println(sup3.get());
    //un metodo q no reciba argumentos y devuevla patata

    System.out.println("******** Ejemplo Stream ***********");
    //Stream<String> s1 = new Stream();
    List<String> myList = new ArrayList<>();

    myList.add("uno");
    myList.add("dos");
    myList.add("venticuatro");
    Stream<String> stream1 = myList.stream();

    myList.parallelStream().filter(s -> s.length()>4).collect(Collectors.toList()).forEach(System.out::println);
    System.out.println(myList);

    Stream.of("uno", "dos");
    Stream <Integer> s3 =  Stream.of(1,2,3);
    IntStream intStream = IntStream.of(1,2,3);

    IntStream.range(1,6).forEach(System.out::println);

    System.out.println("5 ramdom integers");
    new Random().ints(5)
        .forEach(System.out::println);

    System.out.println("4 ramdom integers between 2 and  100");
    new Random().ints(4, 2, 100)
        .forEach(System.out::println);

    Stream.empty();
    Stream.of();

    Item item = new Item("Bici");
    Stream<String> cadenas = Stream.generate(() -> item.getName());
    cadenas.map(s-> s.toUpperCase()).limit(10).
    forEach(System.out::println);
    System.out.println("this is the end");

    //terminal operation foreach, count , collect, findFirst ...
    String [] moustros = {"herman", "Ñily", "Eddie"};
    System.out.println("this is the end");

    System.out.println(Arrays.stream(moustros).count());



    System.out.println(Stream.of(moustros).count());

    System.out.println(Stream.of(moustros).findFirst());

    System.out.println(Stream.of().findFirst());
    System.out.println(Stream.of(moustros).findFirst().get());
    //System.out.println(Stream.of().findFirst().get());
    System.out.println(Stream.of().findFirst().orElse("el de las galletas"));

    //los Streams NO se guarda en memoria, solo fluye una vez, puedes tener operaciones intermedias pero solo una operacion final. Si no hay operacion final no se procesa.
    Stream<String> streamM = Stream.of(moustros);

    //streamM.collect(Collectors.toList());
    //streamM.forEach(System.out::println);


    //List<String> superHeroes = (List<String>) Stream.of("Cable", "Echo", "Alysia", "Cable")
        //.collect(Collectors.toSet());

    List<String> strings = Arrays.asList("this", "is", "a", "long", "list", "of","strings", "to", "use", "as", "a", "demo");

    Map<Integer,List<String>> stringsMapByLength = strings.stream()
        .collect(Collectors.groupingBy(s->s.length()));
    stringsMapByLength.forEach((k,v)-> System.out.printf("%d: %s%n",k,v));
    List<Developer> team = new ArrayList<>();

    Developer polyglot = new Developer("esoteric");
    polyglot.add("clojure");
    polyglot.add("scala");
    polyglot.add("groovy");
    polyglot.add("go");

    Developer busy = new Developer("pragmatic");
    busy.add("java");
    busy.add("javascript");

    team.add(polyglot);
    team.add(busy);

    List<String> teamLanguages = team.stream() //.collect(Collectors.toList());
        .map((Developer d) -> d.getLanguages())
        .flatMap((Set<String>l) -> l.stream())
        .collect(Collectors.toList());

    teamLanguages.forEach(System.out::println);

    //example reduce
    int sum1= IntStream.rangeClosed(1,10).sum();
    System.out.println("sum1="+sum1);
    int sum2= IntStream.rangeClosed(1,10).reduce((s,x) -> {
      System.out.println("s=" +s);
      System.out.println("x="+x);
      return s+x;
    }).getAsInt();
    System.out.println("sum2="+sum2);

    List<Transaction> transactions = getTransactions();

    System.out.println("=====");
    // Biggest Transaction
    Optional<Transaction> biggestTrans = transactions.stream().reduce(
        (t1, t2) -> {
          if(t1.getValue() > t2.getValue()){
            return t1;
          }
          return t2;
        }
    );
    // Optional<Transaction> biggestTrans = transactions.stream().max((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
    biggestTrans.ifPresent(System.out::println);


    System.out.println("=====");

    // Accumulate Transaction amounts
    Transaction accumulateTrans = transactions.stream().reduce(
        new Transaction(null, 0, 0),
        (t1, t2) ->
            new Transaction(t2.getTrader(),
                Integer.max(t1.getYear(), t2.getYear()),
                t1.getValue() + t2.getValue()));

    System.out.println(accumulateTrans);

    //example optional
    Stream<String> streamVacio = Stream.empty();
    Stream<String> streamVacio2 = Stream.empty();
    Stream<String> streamNoVacio = Stream.of("pepe", "luisa");

  //  Optional<String> resultado = streamNoVacio.findFirst();
   // String resultado2 = streamNoVacio.findFirst().get();
    streamNoVacio.findFirst().ifPresent(s-> System.out.println(s));
    //System.out.println(resultado);
    //System.out.println(resultado2);

    String resultado14 = streamVacio2.findFirst().map(s->s.trim()).orElse("valor por defecto");
    System.out.println(resultado14);


  }

  private static List<Transaction> getTransactions() {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    return Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2018, 700),
        new Transaction(alan, 2012, 950));
  }

/*  		System.out.println("*********");
  List<Transaction> transactions = getTransactions();
  Map<Integer,List<Transaction>> mapGroupYear = transactions.stream()
      .collect(Collectors.groupingBy(t->t.getYear()));
		mapGroupYear.forEach((k,v)-> System.out.printf("%d: %s%n",k,v));*/




}