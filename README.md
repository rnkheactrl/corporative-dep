Vulnerability Management

Product
I build a vulnerability management service. People track a vuln - a security vulnerability - from discovery to verified fix.
Core item
The core tracked item is a Vuln, identified by a VulnId.

Status table

| From       | To         | Result    | Business reason |
|------------|------------|-----------|------------------|
| DISCOVERED | CONFIRMED  | Allowed   | Vulnerability validated as real before fix work begins. |
| CONFIRMED  | PATCHED    | Allowed   | A confirmed vulnerability has been fixed. |
| DISCOVERED | PATCHED    | Forbidden | Skips validation before applying a fix. |
| PATCHED    | DISCOVERED | Forbidden | Can't revert an already patched vulnerability. |

Forbidden - why
- DISCOVERED → PATCHED: a fix could be shipped for something never confirmed as a real issue.
- PATCHED → DISCOVERED: erases the record that a fix was already applied, breaking the audit trail.

Running the tests
mvn -q test
