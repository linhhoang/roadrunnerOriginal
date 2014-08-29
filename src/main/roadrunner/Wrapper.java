/*
     RoadRunner - an automatic wrapper generation system for Web data sources
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     This program is  free software;  you can  redistribute it and/or
     modify it  under the terms  of the GNU General Public License as
     published by  the Free Software Foundation;  either version 2 of
     the License, or (at your option) any later version.

     This program is distributed in the hope that it  will be useful,
     but  WITHOUT ANY WARRANTY;  without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
     General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program; if not, write to the:

     Free Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

     ----

     RoadRunner - un sistema per la generazione automatica di wrapper su sorgenti Web
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     Questo  programma �  software libero; �  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come � pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  � distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILIT�  o di  APPLICABILIT� PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne pu�
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

 */
/**
 * Wrapper.java
 *
 *
 * Created: 
 *
 * @author Valter Crescenzi
 * @version
 */

package roadrunner;

import java.io.*;
import java.util.List;
import java.util.logging.*;
import java.net.URL;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.tidy.Out;

import roadrunner.util.FileUtils;
import roadrunner.util.Indenter;
import roadrunner.util.DOMLoader;
import roadrunner.ast.ASTToken;
import roadrunner.ast.Expression;
import roadrunner.ast.ASTEncoder;
import roadrunner.ast.ASTDecoder;
import roadrunner.parser.Extractor;
import roadrunner.parser.BindingException;
import roadrunner.parser.TokenList;
import roadrunner.config.Config;
import roadrunner.config.Preferences;
import roadrunner.marshall.InstanceSerializer;


/**
 * The class of Wrapper
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public class Wrapper
{
    static private Logger log = Logger.getLogger(Wrapper.class.getName());

    final static private String WRAPPER = "wrapper";
    final static private String NAME = "name";


    static private void write(Wrapper wrapper, Writer writer) throws IOException
    {
        PrintWriter out = new PrintWriter(writer);

        Indenter ind = new Indenter(false);
        out.println("<?xml version='1.0' encoding=\"UTF-8\"?>");
        out.println("<" + WRAPPER + " " + NAME + "=\"" + wrapper.getName() + "\">");
        wrapper.getPrefs().encode(ind, out); // encode lexical configuration
        new ASTEncoder(ind, out).encode(wrapper.getExpression());
        ind.dec();
        out.println("</" + WRAPPER + ">");
        out.close();
    }
    
    static private void write(Wrapper wrapper, File file) throws IOException
    {
        PrintWriter out = new PrintWriter(file, "UTF-8");

        Indenter ind = new Indenter(false);
        out.println("<?xml version='1.0' encoding=\"UTF-8\"?>");
        out.println("<" + WRAPPER + " " + NAME + "=\"" + wrapper.getName() + "\">");
        wrapper.getPrefs().encode(ind, out); // encode lexical configuration
        new ASTEncoder(ind, out).encode(wrapper.getExpression());
        ind.dec();
        out.println("</" + WRAPPER + ">");
        out.close();
    } 
    
    
    static private void write(Wrapper wrapper, OutputStream ostream) throws IOException
    {
        PrintWriter out = new PrintWriter(ostream);
        write(wrapper, out);
    }


    /**
     * Read wrapper from a reader
     *
     * @param in
     * @return Wrapper
     * @throws IOException
     * @throws SAXException
     */
    static private Wrapper read(Reader in) throws IOException, SAXException
    {
        Document wrapperDoc = DOMLoader.parseXML(in);
        Wrapper wrapper = readWrapperDocument(wrapperDoc);
        return wrapper;
    }


    /**
     * Method description
     *
     * @param wrapperDoc
     * @return Wrapper
     */
    private static Wrapper readWrapperDocument(Document wrapperDoc)
    {
        // decode lexical preferences of wrapper.
        Preferences prefs = new Preferences();
        prefs.decode(wrapperDoc);
        // decode expression of wrapper
        Expression exp = new ASTDecoder(wrapperDoc).decode();
        Wrapper wrapper = new Wrapper(exp, prefs);
        wrapper.setName(wrapperDoc.getDocumentElement().getAttribute(NAME));
        return wrapper;
    }
    
    
    /**
     * read wrapper from an input stream
     *
     * @param in
     * @return Wrapper
     * @throws SAXException
     * @throws IOException
     */
    static private Wrapper read(InputStream in) throws SAXException, IOException
    {
        Document wrapperDoc = DOMLoader.parseXML(in);
        Wrapper wrapper = readWrapperDocument(wrapperDoc);
        return wrapper;
    }


    /**
     * load Wrapper from a File
     *
     * @param file
     * @return Wrapper
     * @throws IOException
     * @throws SAXException
     */
    public static Wrapper load(File file) throws IOException, SAXException
    {
        return read(new FileReader(file));
    }
    
    
    /**
     * load Wrapper from a byte array
     *
     * @param byteArray
     * @return Wrapper
     * @throws IOException
     * @throws SAXException
     */
    public static Wrapper load(byte[] byteArray) throws IOException, SAXException
    {
        return read(new ByteArrayInputStream(byteArray));
    }
    

    private Expression expression;
    private Preferences prefs;
    private String name;
    private int id;


    /**
     * Constructor
     *
     * @param expression
     * @param options
     */
    public Wrapper(Expression expression, Preferences options)
    {
        this.expression = expression;
        this.prefs = options;
        this.name = null;
        this.id = 0;
    }


    /**
     * get Expression
     *
     * @return Expression
     */
    public Expression getExpression()
    {
        return this.expression;
    }


    /**
     * get Preferences
     *
     * @return Preferences
     */
    public Preferences getPrefs()
    {
        return this.prefs;
    }


    /**
     * get name
     *
     * @return name
     */
    public String getName()
    {
        return this.name;
    }


    void setName(String name)
    {
        this.name = name;
    }


    /**
     * get Id
     *
     * @return Id
     */
    public int getId()
    {
        return this.id;
    }


    void setId(int id)
    {
        this.id = id;
    }


    public Instance wrap(Sample sample) throws BindingException
    {
        return new Extractor(this).extract(sample);
    }


    public void save() throws IOException
    {
        saveAs(new File(this.getName() + ".xml"));
    }


    public void saveAs(File file) throws IOException
    {
//        write(this, new FileWriter(file));
        write(this, file);
    }


    public void saveAs(ByteArrayOutputStream baos) throws IOException
    {
        write(this, baos);
    }

    public String toString()
    {
        return this.getExpression().dump(getName() + ">");
    }


    public boolean equals(Object o)
    {
        Wrapper w = (Wrapper)o;
        return w.getExpression().equals(getExpression()) && w.getPrefs().equals(getPrefs());
    }


    public int hashCode()
    {
        return getExpression().hashCode() + getPrefs().hashCode();
    }


    public static void extract(String argv[]) throws Exception
    {
        int n = 0;
        boolean test = false;
        if (argv.length < 2)
        {
            System.err.println("Usage:\tjava roadrunner.wrapper.Wrapper [-t] <wrapper.xml> "
                               + "<url0> <url1> ... \n"
                               + "\t     -t: test only, do not write extracted data");
            System.exit(-1);
        }
        if (argv[n].toLowerCase().startsWith("-t"))
        {
            test = true;
            n++;
        }
        Wrapper w = null;
        try
        {
            w = Wrapper.load(new File(argv[n++]));
        }
        catch (SAXException saxe)
        {
            System.err.println("Cannot decode wrapper from xml " + saxe);
            System.exit(-1);
        }
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String path =  new File(argv[n++]).getAbsolutePath()+"\\";
        String encoding = argv[n++];
        for (int i = n; i < argv.length; i++)
        {
            out.println("Wrapping: " + argv[i]);
            out.flush();
            Sample sample = null;
            try
            {
                sample = new Sample(new URL(argv[i]), w.getPrefs(), encoding);
                
                Instance data = w.wrap(sample);
                if (!test) {
                    File f = new File(argv[i]);
                    String name = f.getName();
                    name = name.substring(0,name.length()-5);

                    PrintWriter fileOut = new PrintWriter(path+"res_"+name+".xml","UTF-8");
                    fileOut.println("<?xml version=\'1.0\' encoding=\"UTF-8\"?>");
                    fileOut.println("<?xml-stylesheet href=\"..\\.style\\data.xsl\" type=\"text/xsl\"?>");
                    new InstanceSerializer(data,fileOut,new Indenter()).encode();
                    
                    fileOut.close();
                }
            }
            catch (IllegalArgumentException iae)
            {
                System.err.println("Skipping " + argv[i]);
                System.err.println(iae.getMessage());
            }
            catch (IOException ioe)
            {
                System.err.println("Skipping " + argv[i]);
                System.err.println("Cannot reach source:");
                System.err.println(ioe.getMessage());
            }
            catch (SAXException saxe)
            {
                System.err.println("Skipping " + argv[i]);
                System.err.println("Cannot parse xml source:");
                System.err.println(saxe.getMessage());
            }
            catch (BindingException be)
            {
                System.err.println("Skipping " + argv[i]);
                System.err.println("Wrapping failed:");
                System.err.println(be.getMessage());
                writeTokenList(sample.getName(), sample.getTokenlist());
            }
            out.close();
        }
    }


    /**
     * Method description
     *
     * @param name
     * @param tokenlist
     */
    private static void writeTokenList(String name, TokenList tokenlist)
    {
        StringBuffer content = new StringBuffer();
        content.append(name + "\r\n");
        for (ASTToken token : (List<ASTToken>) tokenlist.getTokens())
        {
            content.append(String.format("code:%3s, depth:%3s | %s\r\n", token.code(), token.depth(), token.toString()));
        }
        FileUtils.getInstance().getInstance().createFolder("output/logs/");
        String filePath = "output/logs/" + name;
        FileUtils.getInstance().writeTextFile(content.toString(), filePath, "txt");
    }

}
