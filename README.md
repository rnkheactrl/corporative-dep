# Vulnerability Management

## Product
We build a vulnerability management service. People track a vuln (a security vulnerability) from discovery to verified fix.

## Core item
The core tracked item is a Vuln, identified by a VulnId.

## Status table

| From       | To         | Result    | Business reason |
|------------|------------|-----------|------------------|
| DISCOVERED | CONFIRMED  | Allowed   | Vulnerability validated as real before fix work begins. |
| CONFIRMED  | PATCHED    | Allowed   | A confirmed vulnerability has been fixed. |
| PATCHED    | VERIFIED   | Allowed   | The fix was checked and the vulnerability is gone. |
| DISCOVERED | PATCHED    | Forbidden | Skips validation before applying a fix. |
| PATCHED    | DISCOVERED | Forbidden | Can't revert an already patched vulnerability. |
| VERIFIED   | any        | Forbidden | Stop-factor: a verified vuln is closed for audit. |

## Forbidden: why
- DISCOVERED → PATCHED: a fix could be shipped for something never confirmed as a real issue.
- PATCHED → DISCOVERED: erases the record that a fix was already applied, breaking the audit trail.
- VERIFIED → anything: the verified record is final evidence for audit. A regression is tracked as a new vuln with a new VulnId.

## Rules
`Rule` is a plain Java interface in `domain`. Two implementations collaborate through `RuleChain`:

- `VerifiedIsFinal`: stop-factor, runs first. Nothing leaves VERIFIED.
- `TransitionRule`: the status table above.

The chain is wired with a `@Bean` in `config/RuleConfig`. Spring injects `Rule` into `VulnService`.

## Package diagram

```
  dto            client         handler          config
  ScannerFinding (HTTP later)   (HTTP week 9)    Application
  (JSON later)                                   @Service VulnService
       \              \             /                  |
        \              \           /             injects Rule
         \              \         /
                      domain
             VulnId  VulnStatus  VulnPolicy
             Rule  + TransitionRule, VerifiedIsFinal (RuleChain)
                    (no Spring)
```

Arrows point inward. Domain never imports `org.springframework`; `DomainHasNoSpringTest` fails the build if it does.

## Running
```
mvn -q verify          # tests
mvn spring-boot:run    # starts; logs "VulnService ready: VULN-DEMO DISCOVERED -> CONFIRMED"
```
