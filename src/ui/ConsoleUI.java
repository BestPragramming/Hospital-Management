package ui;

import models.Appointment;
import models.Bill;
import java.util.List;

public class ConsoleUI {

    // ── ANSI codes ────────────────────────────────────────────────────────────
    public static final String RESET    = "[0m";
    public static final String BOLD     = "[1m";
    public static final String DIM      = "[2m";

    public static final String CYAN     = "[36m";
    public static final String WHITE    = "[37m";
    public static final String YELLOW   = "[33m";

    public static final String BR_RED    = "[91m";
    public static final String BR_GREEN  = "[92m";
    public static final String BR_YELLOW = "[93m";
    public static final String BR_BLUE   = "[94m";
    public static final String BR_CYAN   = "[96m";
    public static final String BR_WHITE  = "[97m";

    private static final int BANNER_W  = 62;
    private static final int SECTION_W = 62;

    // ── Banner ────────────────────────────────────────────────────────────────

    public static void printBanner() {
        String title = "HOSPITAL MANAGEMENT SYSTEM";
        int lpad = (BANNER_W - title.length()) / 2;
        int rpad = BANNER_W - title.length() - lpad;
        System.out.println();
        System.out.println(BOLD + BR_CYAN + "  ╔" + "═".repeat(BANNER_W) + "╗" + RESET);
        System.out.println(BOLD + BR_CYAN + "  ║" + " ".repeat(lpad) + BR_WHITE + title + BR_CYAN + " ".repeat(rpad) + "║" + RESET);
        System.out.println(BOLD + BR_CYAN + "  ╚" + "═".repeat(BANNER_W) + "╝" + RESET);
        System.out.println();
    }

    // ── Section header ────────────────────────────────────────────────────────

    public static void printSection(String title) {
        int trailing = SECTION_W - title.length() - 2;
        System.out.println();
        System.out.println(BOLD + BR_BLUE + "  ┌" + "─".repeat(SECTION_W) + "┐" + RESET);
        System.out.println(BOLD + BR_BLUE + "  │  " + BR_WHITE + BOLD + title + BR_BLUE + " ".repeat(Math.max(0, trailing)) + "│" + RESET);
        System.out.println(BOLD + BR_BLUE + "  └" + "─".repeat(SECTION_W) + "┘" + RESET);
    }

    // ── Inline status messages ────────────────────────────────────────────────

    public static void printSuccess(String msg) {
        System.out.println(BR_GREEN + BOLD + "  ✔  " + RESET + BR_GREEN + msg + RESET);
    }

    public static void printAction(String msg) {
        System.out.println(BR_YELLOW + "  ↩  " + YELLOW + msg + RESET);
    }

    // ── Appointments table ────────────────────────────────────────────────────

    public static void printAppointmentsTable(List<Appointment> list) {
        int[] w = {3, 20, 17, 10, 9};
        System.out.println();
        topLine(w);
        headerRow(w, new String[]{"ID", "PATIENT", "DOCTOR", "DATE", "STATUS"});
        midLine(w);
        for (Appointment a : list) {
            String sc = apptStatusColor(a.getStatus());
            dataRow(w, new String[]{
                String.valueOf(a.getId()),
                a.getPatient().getName(),
                a.getDoctor().getName(),
                a.getDate(),
                sc + a.getStatus() + RESET
            });
        }
        botLine(w);
        System.out.println();
    }

    // ── Bills table ───────────────────────────────────────────────────────────

    public static void printBillsTable(List<Bill> bills) {
        int[] w = {3, 20, 10, 28, 6};
        System.out.println();
        topLine(w);
        headerRow(w, new String[]{"ID", "PATIENT", "AMOUNT", "TYPE", "PAID"});
        midLine(w);
        for (Bill b : bills) {
            String pc = b.isPaid() ? BR_GREEN : BR_YELLOW;
            dataRow(w, new String[]{
                String.valueOf(b.getId()),
                b.getPatient().getName(),
                "$" + String.format("%.2f", b.getAmount()),
                b.getBillingType(),
                pc + (b.isPaid() ? "YES" : "NO") + RESET
            });
        }
        botLine(w);
        System.out.println();
    }

    // ── Table drawing helpers ─────────────────────────────────────────────────

    private static void topLine(int[] w) { ruleLine(w, "┌", "┬", "┐"); }
    private static void midLine(int[] w) { ruleLine(w, "├", "┼", "┤"); }
    private static void botLine(int[] w) { ruleLine(w, "└", "┴", "┘"); }

    private static void ruleLine(int[] w, String l, String m, String r) {
        StringBuilder sb = new StringBuilder(BR_CYAN + "  " + l);
        for (int i = 0; i < w.length; i++) {
            sb.append("─".repeat(w[i] + 2));
            if (i < w.length - 1) sb.append(m);
        }
        System.out.println(sb.append(r).append(RESET));
    }

    private static void headerRow(int[] w, String[] cols) {
        StringBuilder sb = new StringBuilder(BR_CYAN + "  │");
        for (int i = 0; i < cols.length; i++) {
            sb.append(BOLD + BR_WHITE + " ").append(pad(cols[i], w[i])).append(" ").append(BR_CYAN + "│");
        }
        System.out.println(sb.append(RESET));
    }

    private static void dataRow(int[] w, String[] vals) {
        StringBuilder sb = new StringBuilder(BR_CYAN + "  │");
        for (int i = 0; i < vals.length; i++) {
            int padding = w[i] - stripAnsi(vals[i]).length();
            sb.append(WHITE + " ").append(vals[i]).append(" ".repeat(Math.max(0, padding))).append(" ").append(BR_CYAN + "│");
        }
        System.out.println(sb.append(RESET));
    }

    private static String apptStatusColor(String status) {
        switch (status) {
            case "SCHEDULED": return BR_GREEN;
            case "CANCELLED": return BR_RED;
            case "COMPLETED": return BR_BLUE;
            default:          return WHITE;
        }
    }

    private static String pad(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static String stripAnsi(String s) {
        return s == null ? "" : s.replaceAll("\\[[;\\d]*m", "");
    }
}
