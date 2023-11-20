package org.example;

import java.util.*;

/*
 * проверка парадокса Монти-Холла на примере трех дверей за одной из которыйх автомобиль
 *
 * 1й вариант: игрок случайно выбирает одну из дверей и не меняет свое решение,
 * и всегда остается при своём первом выборе
 *
 * 2й вариант: игрок случайно выбирает одну из дверей, далее ведущий убирает одну пустую из оставшихся
 * и перелагает сменить дверей на что пользователь соглашается
 *
 * в каждом случае подсчитаем число выигрышей во множестве итераций и переведем в процентное отношение
 *
 * */
public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        Map<Integer, Boolean> result = new HashMap<>();
        int maxSteps = 1000; // число итераций


        // реализация первого варианта
        double counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] doors = getDoors(); //перемешиваем двери
            int selectedDoor = rnd.nextInt(3); // игрок выбирает одну из дверей
            if (doors[selectedDoor]) counterWin++; //проверяем выиграл ли он и сохраняем результат
            result.put(i, doors[selectedDoor]);
        }
        System.out.printf("Результат по первому варианту(не меняя своего выбора) при %d итераций: %.3f%%\n", maxSteps, (counterWin / maxSteps * 100));

        // реализация второго врианта
        counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] doors = getDoors();// перемешиваем двери
            int selectedDoor = rnd.nextInt(3);//игрок выбирает одну из дверей
            selectedDoor = getSecondDoor(selectedDoor, doors); //игрок меняет выбранную дверь на ту, что предложит ведущий
            if (doors[selectedDoor]) counterWin++;
            result.put(i + maxSteps, doors[selectedDoor]);//проверяем выиграл ли он и сохраняем результат
        }
        System.out.printf("Результат по второму варианту (игрок меняет свой выбор) при %d итераций: %.3f%%\n", maxSteps, (counterWin / maxSteps * 100));
    }

    /**
     * Возвращает индекс двери, которую предлагает открыть ведущий
     *
     * @param userSelectedDoor
     * @param doors
     * @return
     */
    private static int getSecondDoor(int userSelectedDoor, boolean[] doors) {
        Random rnd = new Random();
        int selectSecondDoor;
        if (doors[userSelectedDoor]) {
            // начальный выбор игрока был правильный
            while (true) {
                //предлагаем любую из оставшихся дверей
                selectSecondDoor = rnd.nextInt(3);
                if (selectSecondDoor != userSelectedDoor) return selectSecondDoor;
            }
        } else {
            //если начальный выбор был проигрышный,
            // то предлагаем дверь с призом
            while (true) {
                for (int i = 0; i < 3; i++) {
                    if (doors[i]) return i;
                }
            }
        }
    }

    /**
     * Возвращает массив шкатулок, в одной из которого приз
     *
     * @return
     */
    private static boolean[] getDoors() {

        Random rnd = new Random();
        boolean[] doors = new boolean[]{false, false, false};///создание дверей
        doors[rnd.nextInt(3)] = true;/// случайный выбор выйграшной двери
        return doors;
    }
}