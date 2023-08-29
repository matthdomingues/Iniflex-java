import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

// 1 – Classe Pessoa com os atributos: nome (String) e data nascimento (LocalDate).
class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

// 2 – Classe Funcionário que estenda a classe Pessoa, com os atributos: salário (BigDecimal) e função (String).
class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }
}

// 3 – Classe Principal multifuncional
public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 - Funcionários em ordem da tabela
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11,19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993,3,31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994,7,8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003,5,24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996,9,2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        System.out.println("Tabela com todos os funcionários:");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(funcionario -> {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String salarioEditado = decimalFormat.format(funcionario.getSalario());
            salarioEditado = salarioEditado.replace(",", " ").replace(".", ",").replace(" ", ".");
            System.out.println("Salário: " + salarioEditado);

            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        });

        // 3.4 - Aumentar salário em 10%
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.1"));
            funcionario.setSalario(novoSalario);
        });   

        // 3.5/3.6 - Agrupar e imprimir funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("Lista de funcionários agrupados por função:");
        System.out.println();

        funcionariosPorFuncao.forEach((funcao, lista) -> {         
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println("Nome: " + funcionario.getNome()));
            System.out.println();
        });

        // 3.7 - Imprimir funcionários que fazem aniversário em outubro (mês 10) e dezembro (mês 12)
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> System.out.println("O funcionário(a) " + funcionario.getNome() + " faz aniversário no mês " + funcionario.getDataNascimento().getMonthValue() + "."));
        System.out.println();

        // 3.9 - Encontrar funcionário com a maior idade
        Funcionario funcionarioMaisVelho = Collections.max(funcionarios, Comparator.comparing(
                funcionario -> funcionario.getDataNascimento().until(LocalDate.now()).getYears()));
        System.out.println("Funcionário mais velho: " + funcionarioMaisVelho.getNome() + ", Idade: " + funcionarioMaisVelho.getDataNascimento().until(LocalDate.now()).getYears() + ".");
        System.out.println();

        // 3.10 - Ordenar funcionários por ordem alfabética
            List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
            funcionariosOrdenados.sort(Comparator.comparing(funcionario -> funcionario.getNome()));
            System.out.println("Funcionários em ordem alfabética:");
            for (Funcionario funcionario : funcionariosOrdenados) {
                System.out.println("- " + funcionario.getNome());
            }
            System.out.println();
            
        // 3.11 - Imprimir total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String salarioEditado = decimalFormat.format(totalSalarios);
        salarioEditado = salarioEditado.replace(",", " ").replace(".", ",").replace(" ", ".");
        System.out.println("O total dos salários dos funcionários é de: " + salarioEditado);
        System.out.println();

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário}        
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Funcionário(a): " + funcionario.getNome() +
                    ", Salários Mínimos: " + salariosMinimos + ".");
        });
        System.out.println();
    }
}