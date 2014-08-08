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
/*
 * ParserListener.java
 * This interface defines the set of events generated by Parser
 *
 * @author  Valter Crescenzi
 * Created on 24 aprile 2003, 18.00
 */

package roadrunner.parser;

import roadrunner.ast.ASTAnd;
import roadrunner.ast.ASTHook;
import roadrunner.ast.ASTPlus;
import roadrunner.ast.ASTSubtree;
import roadrunner.ast.ASTToken;
import roadrunner.ast.ASTVariant;
import roadrunner.test.ast.*;

public interface ParserListener {
    
  public void startAnd(ASTAnd and);
  public void endAnd(ASTAnd and, boolean matches);
  
  public void startPlus(ASTPlus plus);
  public void endPlus(ASTPlus plus, boolean matches, int times);
  
  public void startHook(ASTHook hook);
  public void endHook(ASTHook hook, boolean matches, int times);
  
  public void startSubtree(ASTSubtree subtree);
  public void endSubtree(ASTSubtree subtree, boolean matches, int firstIndex, int lastIndex);
  
  public void startVariant(ASTVariant variant);
  public void endVariant(ASTVariant variant, boolean matches, Token token);
  
  public void startToken(ASTToken node);
  public void endToken(ASTToken node, boolean matches, Token token);
  
}
