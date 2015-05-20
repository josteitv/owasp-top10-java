public class Html {

    public static String html(String content) {

        return "<!DOCTYPE html>\r\n"
                +
                "<html lang=\"en\">\r\n"
                +
                "  <head>\r\n"
                +
                "    <meta charset=\"utf-8\">\r\n"
                +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
                +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
                +
                "    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->\r\n"
                +
                "\r\n"
                +
                "    <title>OWASP Top 10</title>\r\n"
                +
                "\r\n"
                +
                "    <!-- Bootstrap core CSS -->\r\n"
                +
                "    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n"
                +
                "\r\n"
                +
                "    <!-- Custom styles for this template -->\r\n"
                +
                "    <link href=\"/css/starter-template.css\" rel=\"stylesheet\">\r\n"
                +
                "\r\n"
                +
                "  </head>\r\n"
                +
                "\r\n"
                +
                "  <body>\r\n"
                +
                "\r\n"
                +
                "    <nav class=\"navbar navbar-inverse navbar-fixed-top\">\r\n"
                +
                "      <div class=\"container\">\r\n"
                +
                "        <div class=\"navbar-header\">\r\n"
                +
                "          <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\r\n"
                +
                "            <span class=\"sr-only\">Toggle navigation</span>\r\n" +
                "            <span class=\"icon-bar\"></span>\r\n" +
                "            <span class=\"icon-bar\"></span>\r\n" +
                "            <span class=\"icon-bar\"></span>\r\n" +
                "          </button>\r\n" +
                "          <a class=\"navbar-brand\" href=\"/\">OWASP Top 10</a>\r\n" +
                "        </div>\r\n" +
                "        <div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n" +
                "          <ul class=\"nav navbar-nav\">\r\n" +
                "            <li><a href=\"/search.html\">Search</a></li>\r\n" +
                "            <li><a href=\"/secure/\">Secure area</a></li>\r\n" +
                "          </ul>\r\n" +
                "        </div><!--/.nav-collapse -->\r\n" +
                "      </div>\r\n" +
                "    </nav>\r\n" +
                "\r\n" +
                "    <div class=\"container\">\r\n" +
                "\r\n" +
                "      <div class=\"starter-template\">\r\n" +
                content +
                "      </div>\r\n" +
                "\r\n" +
                "    </div><!-- /.container -->\r\n" +
                "\r\n" +
                "\r\n" +
                "  </body>\r\n" +
                "</html>\r\n" +
                "";

    }

}