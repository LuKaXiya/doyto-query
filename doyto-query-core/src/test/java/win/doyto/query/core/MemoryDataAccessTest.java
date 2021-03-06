package win.doyto.query.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import win.doyto.query.user.UserEntity;
import win.doyto.query.user.UserQuery;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MemoryDataAccessTest
 *
 * @author f0rb
 */
class MemoryDataAccessTest {

    MemoryDataAccess<UserEntity, Integer, UserQuery> mockUserDataAccess;

    private static final int INIT_SIZE = 5;

    @BeforeEach
    void setUp() {
        mockUserDataAccess = new MemoryDataAccess<>(UserEntity.class);
        for (UserEntity entity : initUserEntities()) {
            mockUserDataAccess.create(entity);
        }
    }

    static LinkedList<UserEntity> initUserEntities() {
        LinkedList<UserEntity> userEntities = new LinkedList<>();

        for (int i = 1; i < INIT_SIZE; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(i);
            userEntity.setUsername("username" + i);
            userEntity.setPassword("password" + i);
            userEntity.setEmail("test" + i + "@163.com");
            userEntity.setMobile("1777888888" + i);
            userEntity.setValid(i % 2 == 0);
            userEntities.add(userEntity);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(INIT_SIZE);
        userEntity.setUsername("f0rb");
        userEntity.setNickname("自在");
        userEntity.setPassword("123456");
        userEntity.setEmail("f0rb@163.com");
        userEntity.setMobile("17778888880");
        userEntity.setValid(true);
        userEntities.add(userEntity);
        return userEntities;
    }

    @Test
    void filterByUsername() {
        UserQuery userQuery = UserQuery.builder().username("f0rb").build();
        assertEquals(1, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    void filterByOr() {
        UserQuery userQuery = UserQuery.builder().usernameOrEmailOrMobile("f0rb").build();
        assertEquals(1, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    void filterByLike() {
        UserQuery userQuery = UserQuery.builder().usernameLike("name").build();
        assertEquals(4, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    void filterByIn() {
        List<Integer> idIn = Arrays.asList(1, 2, 3, -1);
        UserQuery userQuery = UserQuery.builder().idIn(idIn).build();
        assertEquals(3, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    void filterByLt() {
        UserQuery userQuery = UserQuery.builder().idLt(3).build();
        assertEquals(2, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    void filterByLe() {
        UserQuery userQuery = UserQuery.builder().idLe(3).build();
        assertEquals(3, mockUserDataAccess.query(userQuery).size());
    }

    @Test
    public void fetch() {
        UserEntity u1 = mockUserDataAccess.get(1);
        UserEntity u2 = mockUserDataAccess.get(1);
        assertSame(u1, u2);

        UserEntity f1 = mockUserDataAccess.fetch(1);
        assertNotSame(u1, f1);
    }

    @Test
    void filterByMultiConditions() {
        UserQuery userQuery = UserQuery.builder().valid(true).usernameOrEmailOrMobileLike("username").build();
        List<UserEntity> userEntities = mockUserDataAccess.query(userQuery);
        assertEquals(2, userEntities.size());
    }

}