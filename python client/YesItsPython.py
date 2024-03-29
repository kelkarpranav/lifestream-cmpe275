

# By Team illuminati

import string,cgi
from os import curdir, sep
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer


class TheHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        try:
            if self.path.endswith(".html"):
                f = open(curdir + sep + self.path) 

                self.send_response(200)
                self.send_header('Content-type',	'text/html')
                self.end_headers()
                self.wfile.write(f.read())
                f.close()
                return
                
            if self.path.endswith(".esp"): 
                self.send_response(200)
                self.send_header('Content-type',	'text/html')
                self.end_headers()
                
                return
                
            return
                
        except IOError:
            self.send_error(404,'File Not Found: %s' % self.path)
     

    def do_POST(self):
        global rootnode
        try:
            ctype, pdict = cgi.parse_header(self.headers.getheader('content-type'))
            if ctype == 'multipart/form-data':
                query=cgi.parse_multipart(self.rfile, pdict)
            self.send_response(301)
            
            self.end_headers()
            upfilecontent = query.get('image')
            print "filecontent", upfilecontent[0]
            self.wfile.write("<HTML>POST OK.<BR><BR> Following is the binary representation of your image: <BR><BR>");
            self.wfile.write(upfilecontent[0]);
            
        except :
            pass

def main():
    try:
        server = HTTPServer(('', 80), TheHandler)
        print 'Upload now !'
        server.serve_forever()
    except KeyboardInterrupt:
        print 'Goodbye!'
        server.socket.close()

if __name__ == '__main__':
    main()

