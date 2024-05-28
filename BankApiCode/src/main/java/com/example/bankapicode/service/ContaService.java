package com.example.bankapicode.service;

import com.example.bankapicode.model.ClienteData;
import com.example.bankapicode.model.ContaData;
import com.example.bankapicode.model.TransferenciaResultado;
import com.example.bankapicode.model.TransacaoResultado;
import com.example.bankapicode.repository.ClienteRepository;
import com.example.bankapicode.repository.ContaRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
    }

    public List<ContaData> findAll() {
        return contaRepository.findAll();
    }

    public ContaData findByNumero_conta(String numero_conta) {
        return contaRepository.findById(numero_conta).orElse(null);
    }

    public ContaData save(ContaData contaData) {
        if(clienteService.findByCpf(contaData.getCliente_cpf())!=null){
            contaData.setNumero_conta(generateAccountNumber());
            contaRepository.save(contaData);
            return contaData;
        }
        else{
            return null;
        }
    }

    public void delete(ContaData contaData) {
        contaRepository.delete(contaData);
    }

    public Optional<ContaData> updateByNumero_conta(String numero_conta, ContaData newContaData) {
        Optional<ContaData> contaData = contaRepository.findById(numero_conta);
        if (contaData.isPresent()) {
            ContaData oldConta = contaData.get();
            oldConta.setSaldo(newContaData.getSaldo());
            oldConta.setLimite_especial(newContaData.getLimite_especial());
            contaRepository.save(oldConta);
            return Optional.of(oldConta);
        } else {
            return Optional.empty();
        }
    }

    public Optional<ContaData> deleteByNumero_conta(String numero_conta) {
        Optional<ContaData> contaData = contaRepository.findById(numero_conta);
        if (contaData.isPresent()) {
            contaRepository.delete(contaData.get());
            return contaData;
        } else {
            return Optional.empty();
        }
    }
    public Optional<ClienteData> getClienteByNumero_conta(String numero_conta) {
        Optional<ContaData> contaData = contaRepository.findById(numero_conta);
        if (contaData.isPresent()) {
            String cpf = contaData.get().getCliente_cpf();
            return clienteRepository.findById(cpf);
        } else {
            return Optional.empty();
        }
    }

    public Optional<TransferenciaResultado> transfer(String tipo, String numero_conta_origem, String numero_conta_destino, Double valor) {
        if(tipo.equalsIgnoreCase("ted")) {
            if(LocalDateTime.now().getDayOfWeek()!= DayOfWeek.SATURDAY && LocalDateTime.now().getDayOfWeek()!=DayOfWeek.SUNDAY) {
                if(LocalDateTime.now().getHour() >= 8 && LocalDateTime.now().getHour() <= 17) {
                    if(valor <= 1000) {
                        return Optional.ofNullable(transferencia(tipo, numero_conta_origem, numero_conta_destino, valor));
                    }
                    else {
                        throw new IllegalArgumentException("O valor deve ser menor que 1000");
                    }
                }
                else {
                    throw new IllegalArgumentException("A hora da transferecia ted deve estar entre 8 e 17");
                }
            }
            else {
                throw new IllegalArgumentException("A transferecia ted nao pode ser feita aos finais de semana");
            }
        }
        else if(tipo.equalsIgnoreCase("pix")) {
            return Optional.ofNullable(transferencia(tipo, numero_conta_origem, numero_conta_destino, valor));
        }
        else {
            throw new IllegalArgumentException("Tipo de transferecia invalida");
        }
    }
    public Optional<TransacaoResultado> depositar(String numero_conta, Double valor) {
        ContaData contaData = contaRepository.findById(numero_conta).get();
        contaData.setSaldo(contaData.getSaldo() + valor);
        contaRepository.save(contaData);
        String emissorCpf = getClienteByNumero_conta(numero_conta).get().getCpf();
        return Optional.of(new TransacaoResultado( valor, emissorCpf, LocalDateTime.now()));
    }

    public Optional<TransacaoResultado> sacar(String numero_conta, Double valor) {
        ContaData contaData = contaRepository.findById(numero_conta).get();
        if(contaData.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        else {
            contaData.setSaldo(contaData.getSaldo() - valor);
            contaRepository.save(contaData);
            String emissorCpf = contaData.getCliente_cpf();
            return Optional.of(new TransacaoResultado(valor, emissorCpf, LocalDateTime.now()));
        }
    }
    public TransferenciaResultado transferencia(String tipo, String numero_conta_origem, String numero_conta_destino, Double valor){
        if(verifyLimit(valor, numero_conta_origem)) {
            throw new IllegalArgumentException("Limite ultrapassado");
        }else{
            ContaData contaOrigem = contaRepository.findById(numero_conta_origem).get();
            ContaData contaDestino = contaRepository.findById(numero_conta_destino).get();

            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);

            contaRepository.save(contaOrigem);
            contaRepository.save(contaDestino);

            String cpfOrigem = verifyClientExistence(numero_conta_origem);
            String cpfDestino = verifyClientExistence(numero_conta_destino);

            return new TransferenciaResultado(tipo, valor, cpfOrigem, cpfDestino, LocalDateTime.now());
        }
    }
    public String verifyClientExistence(String numero_conta) {
        if(contaRepository.findById(numero_conta).isPresent()) {
            return getClienteByNumero_conta(numero_conta).get().getCpf();
        }
        else {
            return null;
        }
    }
    public String generateAccountNumber(){
        Random random = new Random();
        int newId = 0;
        do {
            Integer randomId = random.nextInt(0, 9);
            Integer randomId2 = random.nextInt(0, 9);
            Integer randomId3 = random.nextInt(0, 9);
            Integer randomId4 = random.nextInt(0, 9);
            int randomId5 = (randomId + randomId2 + randomId3 + randomId4) % 10;

            newId = Integer.parseInt(randomId.toString()
                    .concat(randomId2.toString())
                    .concat(randomId3.toString())
                    .concat(randomId4.toString())
                    .concat(Integer.toString(randomId5)));
        }while (contaRepository.findById(Integer.toString(newId)).isPresent());
        return Integer.toString(newId);
    }
    public boolean verifyLimit(Double valor, String numero_conta) {
        ContaData contaData = contaRepository.findById(numero_conta).get();
        return contaData.getLimite_especial() + contaData.getSaldo() > valor;
    }
}
