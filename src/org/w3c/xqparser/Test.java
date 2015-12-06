/*
 Copyright (c) 2005 W3C(r) (http://www.w3.org/) (MIT (http://www.lcs.mit.edu/),
 INRIA (http://www.inria.fr/), Keio (http://www.keio.ac.jp/)),
 All Rights Reserved.
 See http://www.w3.org/Consortium/Legal/ipr-notice-20000612#Copyright.
 W3C liability
 (http://www.w3.org/Consortium/Legal/ipr-notice-20000612#Legal_Disclaimer),
 trademark
 (http://www.w3.org/Consortium/Legal/ipr-notice-20000612#W3C_Trademarks),
 document use
 (http://www.w3.org/Consortium/Legal/copyright-documents-19990405),
 and software licensing rules
 (http://www.w3.org/Consortium/Legal/copyright-software-19980720)
 apply.
 */
// ONLY EDIT THIS FILE IN THE GRAMMAR ROOT DIRECTORY!
// THE ONE IN THE ${spec}-src DIRECTORY IS A COPY!!!
package org.w3c.xqparser;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import org.bson.types.ObjectId;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.conf.ParamType;
import wrapper.Wrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 *
 * @author
 * @version 1.0
 */
@SuppressWarnings("all")
public class Test {
    private PrintStream original;
    private PrintStream devNull;

    public Test() {
        original = System.out;
        devNull = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //
            }
        });
    }

    public static void main(String args[]) {
        Test test = new Test();
        try {
            test.parse((new XPath("for $a in document('personne')//personne where $a/nom='Dupont' return $a")));
            test.parse((new XPath("for $a in document('personne')//formation where $a/nom='INFO' return $a")));
            test.parse((new XPath("for $a in document('Personne')//Nom where $a='Dupont' return $a")));
            test.parse((new XPath("for $a in document('personne')//personne, $b in document('formation')//formation where $a/idFormation=58 return $a")));
            test.parse((new XPath("for $a in document('personne')//personne, $b in document('formation')//formation where $a/idFormation=$b/id return $a")));
            test.parse((new XPath("for $a in document('personne')//nom, $b in document('formation')//nom where $a='Dupont' return $a")));
            test.parse((new XPath("for $a in document('personne')//nom, $b in document('formation')//nom where $a=$b return $a")));
            test.parse((new XPath("for $a in document('personne')//nom, $b in document('formation')//nom where $a=$b and $a='Dupont' return $a")));
            test.parse((new XPath("for $a in document(\"Personne\")//Ecole where $a/Nom=\"INSA de Rennes\" return $a")));
            test.parse((new XPath("for $a in document('Personne')//Formation/Ecole where $a/Nom='INSA de Rennes' return $a")));
            test.parse((new XPath("for $a in document('Personne')//Personne, $b in document('Ecole')//Ecole where $a/Nom = 1 return $a")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void parse(XPath q) throws ParseException {
        System.setOut(devNull);
        Wrapper query = q.XPath2();
        System.setOut(original);
        System.out.println(query.getMongodb().get());
        System.out.println(query.getSql().getSQL(ParamType.INLINED));
    }
}