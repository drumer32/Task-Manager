package managers;

public class Managers {
    public static TaskManager getDefault(Integer number) {
        TaskManager result = null;
        if (number == 1) {
            result = new InMemoryTaskManager();
        } else if (number == 2) {
            result = new FileBackedTaskManager("TaskSavedBackup");
        } else {
            System.out.println("ERROR!!! ВВЕДИТЕ IN_MEMORY ИЛИ IN_FILE");
        }
        return result;
    }
}

//Большое спасибо за ревью! Не смог вчера подправить, забыл дома комп включить для удаленного доступа.
// А на работе никак)

//По идее, с такой реализацией даже не нужно два разных класса для main. Можно менять параметры и все)
// Но я пока что оставлю два. Вдруг потом нужен будет
