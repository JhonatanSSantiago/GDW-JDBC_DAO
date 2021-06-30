/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.dao;
import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author jhons
 */
public class PessoaDaoXml implements PessoaDaoInterface{
     private Document doc;
    private String caminho;
    int cod;
    public PessoaDaoXml(String caminho) throws ErroDAOException
    {
        try {
            DocumentBuilderFactory fabrica=DocumentBuilderFactory.newInstance();
            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            doc=construtor.parse(caminho);
            this.caminho=caminho;
            inicializaCodigo();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new ErroDAOException(ex);
        }
    }
    private void inicializaCodigo()
    {
        NodeList pessoas=doc.getDocumentElement().getElementsByTagName("pessoa");
        int quant=pessoas.getLength();
        Element ultimo=(Element)pessoas.item(quant-1);
        cod=1+Integer.parseInt(ultimo.getElementsByTagName("codigo").item(0).getFirstChild().getNodeValue());
    }
    
    @Override
    public void criaPessoa(Pessoa p) throws ErroDAOException {
        p.setCodigo(cod);
        Element pessoa=doc.createElement("pessoa");
        Element codigo=doc.createElement("codigo");
        Element nome=doc.createElement("nome");
        Element idade=doc.createElement("idade");
        pessoa.appendChild(codigo);
        pessoa.appendChild(nome);
        pessoa.appendChild(idade);
        codigo.appendChild(doc.createTextNode(String.valueOf(p.getCodigo())));
        nome.appendChild(doc.createTextNode(p.getNome()));
        idade.appendChild(doc.createTextNode(String.valueOf(p.getIdade())));
        doc.getDocumentElement().appendChild(pessoa);
        salvar();
        cod++;
    }
    private Pessoa deNoParaPessoa(Element no)
    {
        Pessoa p=new Pessoa();
        int codigo=Integer.parseInt(no.getElementsByTagName("codigo").item(0).getFirstChild().getNodeValue());
        String nome=no.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
        int idade=Integer.parseInt(no.getElementsByTagName("idade").item(0).getFirstChild().getNodeValue());
        p.setCodigo(codigo);
        p.setIdade(idade);
        p.setNome(nome);
        return p;
    }
    
    @Override
    public List<Pessoa> pegaPessoas() throws ErroDAOException {
        List<Pessoa> pessoas=new ArrayList<>();
        NodeList listaPessoas=doc.getDocumentElement().getElementsByTagName("pessoa");
        int quant=listaPessoas.getLength();
        for(int i=0;i<quant;i++)
        {
            Pessoa p=deNoParaPessoa((Element)listaPessoas.item(i));
            pessoas.add(p);
        }
        return pessoas;
    }
    private void salvar()  throws ErroDAOException
    {
        try {
            TransformerFactory fabrica=TransformerFactory.newInstance();
            Transformer transformador=fabrica.newTransformer();
            DOMSource fonte=new DOMSource(doc);
            File file=new File(caminho);
            StreamResult saida=new StreamResult(file);
            transformador.transform(fonte, saida);
        } catch (TransformerException ex) {
            throw new ErroDAOException(ex);
        }
    }
    @Override
    public void sair() throws ErroDAOException {
        
    }
    private String serealizar(Node no) throws ErroDAOException
    {
        try {
            TransformerFactory fabrica=TransformerFactory.newInstance();
            Transformer transformador=fabrica.newTransformer();
            DOMSource fonte=new DOMSource(no);
            ByteArrayOutputStream fluxo=new ByteArrayOutputStream();
            StreamResult saida=new StreamResult(fluxo);
            transformador.transform(fonte, saida);
            return fluxo.toString();
        } catch (TransformerException ex) {
            throw new ErroDAOException(ex);
        }
    }
    @Override
    public Pessoa pegaPessoa(int codigo) throws ErroDAOException {
        Pessoa pessoa = null;
        NodeList listaCodigo=doc.getDocumentElement().getElementsByTagName("codigo");
        int quant=listaCodigo.getLength();
        for(int i=0;i<quant;i++){
        Node noCodigo = listaCodigo.item(i);
        String codigoText = noCodigo.getFirstChild().getNodeValue();
        int codigoConvertido = Integer.parseInt(codigoText);
            if (codigoConvertido == codigo) {
                pessoa=deNoParaPessoa((Element)noCodigo.getParentNode());                
            }
        }
        return pessoa;
    }

    @Override
    public void deletarPessoa(int codigo) throws ErroDAOException {
        NodeList listaCodigo=doc.getDocumentElement().getElementsByTagName("codigo");
        int quant=listaCodigo.getLength();
        for(int i=0;i<quant;i++){
        Node noCodigo = listaCodigo.item(i);
        String codigoText = noCodigo.getFirstChild().getNodeValue();
        int codigoConvertido = Integer.parseInt(codigoText);
            if (codigoConvertido == codigo) {
               Node noPessoa = noCodigo.getParentNode();              
               noPessoa.getParentNode().removeChild(noPessoa);
               salvar();
            }
        }
        serealizar(doc); 
    }

    @Override
    public void deletarPessoa(Pessoa p) throws ErroDAOException {
        deletarPessoa(p.getCodigo());
    }

    @Override
    public void editarPessoa(Pessoa p) throws ErroDAOException {
        String pessoaCodigoText = String.valueOf(p.getCodigo());
        NodeList listaCodigo=doc.getDocumentElement().getElementsByTagName("codigo");
        int quant=listaCodigo.getLength();
        for(int i=0;i<quant;i++){
            Node noCodigo = listaCodigo.item(i);
            String codigoText = noCodigo.getFirstChild().getNodeValue();
            if (codigoText.equals(pessoaCodigoText)) {
               Element noPessoa = (Element)noCodigo.getParentNode();
              // String nome=noPessoa.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
               //int idade=Integer.parseInt(noPessoa.getElementsByTagName("idade").item(0).getFirstChild().getNodeValue());
               noPessoa.getElementsByTagName("nome").item(0).getFirstChild().setNodeValue(p.getNome());
               noPessoa.getElementsByTagName("idade").item(0).getFirstChild().setNodeValue(String.valueOf(p.getIdade()));
               salvar();
            }
        }
        serealizar(doc);
    }

}
