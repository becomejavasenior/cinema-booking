package ua.cinemabooking.service.impl;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.service.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Service
@Async
public class EmailServiceImpl implements EmailService {

    private Session session;
    private String host="smtp.gmail.com";
    private String user="cinemabecomejavasenior@gmail.com";
    private String password="becomejavasenior0218";

    public void init() {
        Properties props = new Properties();

        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session  = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });
    }

    @Override
    public void sendMessage(String textMessage, String email) {
        sendMessage(textMessage, "Cinema Becomejavasenior" , email);
    }

    //тут надо добавить конвертер и через него пропустить лист файлов, после чего закинуть в контент massage
    @Override
    public void sendMessage(String textMessage, String subject, String email) {
        try {
            Message message = new MimeMessage(session);






            BillOrder billOrder = new BillOrder();

            List<File> fileList = new LinkedList<>();

            StringBuilder sb = new StringBuilder();

            Seans seans = billOrder.getSeans();
            sb.append("Фильм: ").append(seans.getMovie().getName()).append("\n");
            sb.append("Сеанс: ").append("c ").append(seans.getStart()).append(" до ").append(seans.getEnd()).append("\n");

            String rowString = new String("Ряд: ");

            String placeString = new String("Место: ");

            List<String> stringList = new LinkedList<>();

            billOrder.getPlaceSet().forEach((place -> {
                stringList.add(sb.toString()+rowString+place.getY()+"\n"+placeString+place.getX()+"\n");
            }));

            stringList.forEach((bills) ->{

                File file = new File(".");

                try(FileWriter fileWriter = new FileWriter(file)) {

                    fileWriter.write(bills);

                    fileWriter.flush();

                    fileList.add(file);

                    file.delete();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });


            //сюда надо добавить конвертер, и добавить в контент самого massage


//            MimeBodyPart attachment = new MimeBodyPart();
//            Multipart multipart = new MimeMultipart();
//
//
//            attachment.attachFile("", "application/pdf", "base64");


//            for (String filePath : attachFiles) {
//                MimeBodyPart attachPart = new MimeBodyPart();
//
//                try {
//                    attachPart.attachFile(filePath);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//
//                multipart.addBodyPart(attachPart);
//
//                multipart.addBodyPart(attachment);
//
//            }
//                message.setContent(multipart);
//
//
//
//            String pathTofile;
//
//
//            multipart.addBodyPart();

            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject(subject);
            message.setText(textMessage);

            //send the message
            Transport.send(message);
            System.out.println("message sent successfully...");
        } catch (MessagingException e) {e.printStackTrace();}
    }


    public static void main(String[] args) throws IOException {
        File inputFile = new File("document.txt");
        File outputFile = new File("document.pdf");

        try(FileWriter ff = new FileWriter(inputFile)) {


            ff.write("I mykjnjksndfjkvdfkj");
            ff.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


        doGenerate("document.txt");

//        outputFile = createPDF(inputFile, outputFile);

        if (outputFile.length() > 0){

            System.out.println("Что-то есть");

        }else System.out.println("Нихуя нету");
//        IConverter converter = RemoteConverter.builder()
//                .baseFolder(new File("/Users/macbookair/documents4j/temp"));
//                           .workerPool(20, 25, 2, TimeUnit.SECONDS)
//                .processTimeout(5, TimeUnit.SECONDS)
//                .build(); ;
//        Future<Boolean> conversion = converter
//                .convert(inputFile).as(DocumentType.MS_WORD)
//                .to(outputFile).as(DocumentType.PDF)
//                .prioritizeWith(1000) // optional
//                .schedule();

//        EasyPDF.initialize();
//
//        IPrinter printer = new IPrinter();
//
//        IPrintJob pj = printer.getPrintJob();
//        pj.PrintOut(inputFileName, outputFileName);
//
//        EasyPDF.uninitialize();

// connect to an OpenOffice.org instance running on port 8100
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
//        connection.connect();
//
//// convert
//        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//        converter.convert(inputFile, outputFile);
//
//// close the connection
//        connection.disconnect();
    }

//    private static File createPDF(File inputFile, File outputFile) {
//        try {
//            long start = System.currentTimeMillis();
//
//            // 1) Load DOCX into WordprocessingMLPackage
//            InputStream is = new FileInputStream(inputFile);
//            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
//                    .load(is);
//
//            // 2) Prepare Pdf settings
//            PdfSettings pdfSettings;
//            pdfSettings = new PdfSettings();
//
//            // 3) Convert WordprocessingMLPackage to Pdf
//            OutputStream out = new FileOutputStream(outputFile);
//            PdfConversion converter = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
//                    wordMLPackage);
//            converter.output(out, pdfSettings);
//
//            System.err.println("Generate pdf/HelloWorld.pdf with "
//                    + (System.currentTimeMillis() - start) + "ms");
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//        return outputFile;
//    }

    protected static void doGenerate(String fileInName) throws IOException {
//        String root="tar;
        String fileOutName= fileInName.substring(0, fileInName.indexOf("."))+ ".pdf";
        long startTime=System.currentTimeMillis();
        InputStream inputStream = new FileInputStream(fileInName);
        XWPFDocument document=new XWPFDocument(inputStream);
        OutputStream out=new FileOutputStream(new File(fileOutName));
        PdfOptions options=PdfOptions.create().fontEncoding("windows-1250");
        PdfConverter.getInstance().convert(document,out,options);

        System.out.println("Generate " + fileOutName + " with "+ (System.currentTimeMillis() - startTime)+ " ms.");
    }


}
