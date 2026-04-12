package com.example.Smart_canteen;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.Smart_canteen.model.MenuItem;
import com.example.Smart_canteen.model.User;
import com.example.Smart_canteen.model.CanteenStatus;
import com.example.Smart_canteen.repository.MenuRepository;
import com.example.Smart_canteen.repository.UserRepository;
import com.example.Smart_canteen.repository.CanteenStatusRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final CanteenStatusRepository canteenStatusRepository;

    public DataInitializer(MenuRepository menuRepository, 
                          UserRepository userRepository,
                          CanteenStatusRepository canteenStatusRepository) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.canteenStatusRepository = canteenStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize users if needed
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin@canteen.com", "admin123", "Admin", "ADMIN"));
            userRepository.save(new User("faculty@canteen.com", "fac123", "Faculty One", "FACULTY"));
            userRepository.save(new User("student@canteen.com", "stud123", "Student One", "STUDENT"));
        }

        // Initialize canteen status if needed
        if (canteenStatusRepository.count() == 0) {
            CanteenStatus status = new CanteenStatus();
            status.setId(1);
            status.setOpen(false);
            canteenStatusRepository.save(status);
        }

        // Initialize menu items if needed
        if (menuRepository.count() == 0) {
            // Tiffin items
            menuRepository.save(new MenuItem(null, "Masala Dosa", 40.0, "tiffin", true, "/images/masala-dosa.jpg"));
            menuRepository.save(new MenuItem(null, "Uttapam", 40.0, "tiffin", true, "/images/uttapam.jpg"));
            menuRepository.save(new MenuItem(null, "Egg Dosa", 50.0, "tiffin", true, "/images/Egg Dosa.jpg"));
            menuRepository.save(new MenuItem(null, "Bread Omelette", 40.0, "tiffin", true, "/images/Bread Omelette.jpg"));

            // Meals items
            menuRepository.save(new MenuItem(null, "Rice", 30.0, "meals", true, "/images/rice.jpg"));
            menuRepository.save(new MenuItem(null, "Sambar", 20.0, "meals", true, "/images/sambar.jpg"));
            menuRepository.save(new MenuItem(null, "Rasam", 20.0, "meals", true, "/images/rasam.jpg"));
            menuRepository.save(new MenuItem(null, "Pappu", 30.0, "meals", true, "/images/pappu.jpg"));
            menuRepository.save(new MenuItem(null, "Lemon Rice", 30.0, "meals", true, "/images/Lemon Rice.jpg"));
            menuRepository.save(new MenuItem(null, "Curd Rice", 30.0, "meals", true, "/images/Curd Rice.jpg"));
            menuRepository.save(new MenuItem(null, "Tomato Rice", 35.0, "meals", true, "/images/Tomato Rice.jpg"));
            menuRepository.save(new MenuItem(null, "Veg Pulao", 50.0, "meals", true, "/images/Veg Pulao.jpg"));

            // Chinese items
            menuRepository.save(new MenuItem(null, "Gobi Manchurian", 70.0, "chinese", true, "/images/gobi-manchurian.jpg"));
            menuRepository.save(new MenuItem(null, "Egg Fried Rice", 70.0, "chinese", true, "/images/Egg Fried Rice.jpg"));
            menuRepository.save(new MenuItem(null, "Paneer Fried Rice", 80.0, "chinese", true, "/images/Paneer Fried Rice.jpg"));
            menuRepository.save(new MenuItem(null, "Veg Noodles", 55.0, "chinese", true, "/images/noodles.jpg"));
            menuRepository.save(new MenuItem(null, "Noodles", 60.0, "chinese", true, "/images/noodles.jpg"));

            // Non-veg items
            menuRepository.save(new MenuItem(null, "Chicken Curry", 90.0, "non-veg", true, "/images/chicken curry.jpg"));

            // Veg items
            menuRepository.save(new MenuItem(null, "Fried Rice", 70.0, "veg", true, "/images/Fried Rice.jpg"));

            // Snacks items
            menuRepository.save(new MenuItem(null, "Samosa", 15.0, "snacks", true, "/images/Samosa.jpg"));
            menuRepository.save(new MenuItem(null, "Mirchi Bajji", 15.0, "snacks", true, "/images/Mirchi Bajji.jpg"));
            menuRepository.save(new MenuItem(null, "Veg Puff", 20.0, "snacks", true, "/images/Veg Puff.jpg"));
            menuRepository.save(new MenuItem(null, "Egg Puff", 25.0, "snacks", true, "/images/Egg Puff.jpg"));
            menuRepository.save(new MenuItem(null, "Veg Sandwich", 35.0, "snacks", true, "/images/Veg Sandwich.jpg"));
            menuRepository.save(new MenuItem(null, "Egg Sandwich", 40.0, "snacks", true, "/images/Egg Sandwich.jpg"));

            // Beverages items
            menuRepository.save(new MenuItem(null, "Coffee", 10.0, "beverages", true, "/images/coffee.jpg"));
            menuRepository.save(new MenuItem(null, "Tea", 10.0, "beverages", true, "/images/coffee.jpg"));
            menuRepository.save(new MenuItem(null, "Pineapple Juice", 25.0, "beverages", true, "/images/pinaple juice.jpg"));
            menuRepository.save(new MenuItem(null, "Buttermilk", 10.0, "beverages", true, "/images/buttermilk.jpg"));
            menuRepository.save(new MenuItem(null, "Mango Juice", 25.0, "beverages", true, "/images/Mango Juice.jpg"));
            menuRepository.save(new MenuItem(null, "Watermelon Juice", 25.0, "beverages", true, "/images/Watermelon Juice.jpg"));
            menuRepository.save(new MenuItem(null, "Badam Milk", 25.0, "beverages", true, "/images/Badam Milk.jpg"));
            menuRepository.save(new MenuItem(null, "Lemon Juice", 15.0, "beverages", true, "/images/idly.jpg"));
            menuRepository.save(new MenuItem(null, "Lassi", 20.0, "beverages", true, "/images/Lassi.jpg"));
        }
    }
}
