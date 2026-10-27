#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <stdexcept>
using namespace std;
class SecureLogger {

ofstream logfile;
string timestamp() {
time_t now = time(0);
char buf[20];
strftime(buf, sizeof(buf), "%Y-%m-%d %H:%M:%S", localtime(&now));
return string(buf);
}
public:
SecureLogger(const string& path) {
logfile.open(path, ios::app);
if (!logfile) throw runtime_error("Cannot open log file");
}
~SecureLogger() { if (logfile.is_open()) logfile.close(); }
void log(const string& level, const string& msg) {
if (msg.empty())
throw invalid_argument("Empty log message not allowed");
string entry = "[" + timestamp() + "] [" + level + "] " + msg;
logfile << entry << endl;
cout << entry << endl;
}
};
int main() {
try {
SecureLogger logger("/tmp/secure_app.log");
logger.log("INFO", "Application started");
logger.log("WARNING", "High memory usage detected");

logger.log("ERROR", "Connection refused on port 8080");
try {
logger.log("DEBUG", "");
} catch(const invalid_argument& e) {
cout << "Caught: " << e.what() << endl;
}
cout << "Logging complete." << endl;
} catch(const exception& e) {
cerr << "Logger error: " << e.what() << endl;
}
return 0;
}
