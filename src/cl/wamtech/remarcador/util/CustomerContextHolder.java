package cl.wamtech.remarcador.util;

public class CustomerContextHolder {

	   private static final ThreadLocal<CustomerType> contextHolder =
	            new ThreadLocal<CustomerType>();

	   public static void setCustomerType(CustomerType customerType) {
	      contextHolder.set(customerType);
	   }

	   public static CustomerType getCustomerType() {
	      return (CustomerType) contextHolder.get();
	   }

	   public static void clearCustomerType() {
	      contextHolder.remove();
	   }
	}

