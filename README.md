# URL-Parser
Java application that sums up sizes of all images on a website and enables following the links.

Application stores all visited URLs in SQLite database (the new file 'visited_links.db' is created). History can be easily deleted in 'Edit' menu bar.

As every picture has to be handled separately, multithreading is used.

GUI is implemented using JavaFX platform.

Parsing documents and connecting with URLs are realised with Jsoup and URLConnection.
