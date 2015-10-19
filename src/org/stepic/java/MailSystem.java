package org.stepic.java;

import java.util.logging.Logger;

/**
 * Created by Mikhail Valeyko on 19/10/2015.
 */
public class MailSystem {
    public static final String AUSTIN_POWERS = "Austin Powers";
    public static final String WEAPONS = "weapons";
    public static final String BANNED_SUBSTANCE = "banned substance";

    /*
    ?????????: ????????, ??????? ????? ????????? ?? ?????.
    ? ????? ???????? ????? ???????? ?? ???? ? ???? ???????????? ??????.
    */
    public static interface Sendable {
        String getFrom();
        String getTo();
    }

    /*
    ??????????? ?????,??????? ????????? ?????????????? ?????? ????????
    ????????? ? ?????????? ?????? ? ??????????????? ????? ??????.
    */
    public static abstract class AbstractSendable implements Sendable {

        protected final String from;
        protected final String to;

        public AbstractSendable(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AbstractSendable that = (AbstractSendable) o;

            if (!from.equals(that.from)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }

    }

    /*
    ??????, ? ???????? ???? ?????, ??????? ????? ???????? ? ??????? ?????? `getMessage`
    */
    public static class MailMessage extends AbstractSendable {

        private final String message;

        public MailMessage(String from, String to, String message) {
            super(from, to);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailMessage that = (MailMessage) o;

            if (message != null ? !message.equals(that.message) : that.message != null) return false;

            return true;
        }

    }

    /*
    ???????, ?????????? ??????? ????? ???????? ? ??????? ?????? `getContent`
    */
    public static class MailPackage extends AbstractSendable {
        private final Package content;

        public MailPackage(String from, String to, Package content) {
            super(from, to);
            this.content = content;
        }

        public Package getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailPackage that = (MailPackage) o;

            if (!content.equals(that.content)) return false;

            return true;
        }

    }

    /*
    ?????, ??????? ?????? ???????. ? ??????? ???? ????????? ???????? ??????????? ? ????????????? ????????.
    */
    public static class Package {
        private final String content;
        private final int price;

        public Package(String content, int price) {
            this.content = content;
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Package aPackage = (Package) o;

            if (price != aPackage.price) return false;
            if (!content.equals(aPackage.content)) return false;

            return true;
        }
    }

    /*
    ?????????, ??????? ?????? ?????, ??????? ????? ?????-???? ??????? ?????????? ???????? ??????.
    */
    public static interface MailService {
        Sendable processMail(Sendable mail);
    }

    /*
    ?????, ? ??????? ?????? ?????? ????????? ?????
    */
    public static class RealMailService implements MailService {

        @Override
        public Sendable processMail(Sendable mail) {
            // ????? ?????? ??? ????????? ??????? ???????? ?????.
            return mail;
        }
    }

    public static class UntrustworthyMailWorker implements MailService {
        private MailService[] fakeMailServices;
        private RealMailService realMailService;

        public UntrustworthyMailWorker(MailService[] fakeMailServices) {
            this.fakeMailServices = fakeMailServices;
            realMailService = new RealMailService();
        }

        public RealMailService getRealMailService() {
            return realMailService;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            for (MailService mailService : fakeMailServices) {
                mail = mailService.processMail(mail);
            }
            return realMailService.processMail(mail);
        }
    }

    public static class Spy implements MailService {
        private final Logger LOGGER;

        public Spy(Logger logger) {
            this.LOGGER = logger;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailMessage) {
                if (mail.getFrom().equals(AUSTIN_POWERS) || mail.getTo().equals(AUSTIN_POWERS)) {
                    LOGGER.warning("Detected target mail correspondence: from {from} to {to} \"{message}\"");
                } else {
                    LOGGER.info("Usual correspondence: from {from} to {to}");
                }
            }
            return mail;
        }
    }

    public static class Thief implements MailService {
        private final int MIN_PRICE;
        private int stolenValue;

        public Thief(int minPrice) {
            MIN_PRICE = minPrice;
            stolenValue = 0;
        }

        public int getStolenValue() {
            return stolenValue;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                // TODO: implement
            }
            return null;
        }
    }

    public static class Inspector implements MailService {
        @Override
        public Sendable processMail(Sendable mail) {
            return null;
        }
    }
}
