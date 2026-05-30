# Defect Report Sample

## Defect ID

`BUG-001`

## Title

Billing summary shows outdated amount after successful payment refresh

## Severity

High

## Priority

High

## Environment

UAT

## Module

Customer Billing

## Summary

After a successful payment is submitted, the billing summary panel still shows the previous due amount until the user manually refreshes the page.

## Steps To Reproduce

1. Log in with a valid customer account.
2. Open the billing page.
3. Pay the current due amount using a valid payment method.
4. Wait for the payment success confirmation.
5. Review the billing summary panel.

## Expected Result

The due amount should update immediately after the payment is confirmed.

## Actual Result

The payment succeeds, but the summary still displays the old due amount until the page is refreshed.

## Business Impact

This creates confusion for customers, can increase duplicate payment attempts, and reduces confidence in billing accuracy.

## Evidence

- screenshot after payment success
- console and network capture if available
- affected account sample ID

## Suggested Follow-up

Verify whether the summary widget is missing a refresh event after payment confirmation or if cached data is being reused.
