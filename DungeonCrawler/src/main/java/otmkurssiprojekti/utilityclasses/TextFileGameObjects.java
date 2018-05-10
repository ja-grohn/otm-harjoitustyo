/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.utilityclasses;

import static java.lang.Integer.parseInt;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import otmkurssiprojekti.domain.gameobject.archetypes.NonPlayerCharacterArchetype;
import otmkurssiprojekti.domain.gameobject.gamecharacter.nonplayercharacter.HostileNonPlayerCharacter;
import otmkurssiprojekti.domain.gameobject.gamecharacter.playercharacter.BasicPlayerCharacter;
import otmkurssiprojekti.domain.gameobject.gameinanimates.LevelLink;
import otmkurssiprojekti.domain.gameobject.interfaces.derivatives.NonPlayerCharacter;
import otmkurssiprojekti.domain.gameobject.interfaces.derivatives.PlayerCharacter;
import otmkurssiprojekti.domain.gameobject.location.Coords;
import static otmkurssiprojekti.domain.gameobject.location.Direction.DOWN;

/**
 *
 * @author Juho Gröhn
 */
public class TextFileGameObjects {

    /**
     * Converts string of the form [0-9]+,[0-9]+,[0-9]+ into coords. Example:
     * "0,9,11" becomes new Coords(0, 9, 11). "-1,3,22" becomes new Coords(-1,
     * 3, 22)
     *
     * @param attr The coords as a string.
     * @return The converted coords.
     */
    public static Coords makeCoords(String attr) throws IllegalArgumentException {
        if (!attr.matches("[0-9]+,[0-9]+,[0-9]+")) {
            throw new IllegalArgumentException();
        }

        String[] attrs = attr.split(",");
        int x = parseInt(attrs[0]);
        int y = parseInt(attrs[1]);
        int z = parseInt(attrs[2]);

        return new Coords(x, y, z);
    }

    /**
     * Inverse operation of makeCoords():{@link #makeCoords(java.lang.String)}.
     *
     * @param coords Coords.
     * @return The TextFileGameLevels compatible string representation of the
     * given coords.
     */
    public static String printCoords(Coords coords) {
        return coords.getX() + "," + coords.getY() + "," + coords.getZ();
    }

    /**
     * Creates an archetype based on the enumerator class as well as the string
     * identity given. Example: (PlayercharacterArchetype.class, "ASSASSIN")
     * becomes PlayerCharacterArchetype.ASSASSIN
     *
     * @param <T> An enumerator class.
     * @param t The enumerator class from which the object is to be obtained.
     * @param attr The string by which the enumerator object is identified.
     * @return Returns the enumerator that matches the string, if one exists.
     */
    public static <T extends Enum<?>> Optional<T> makeArcheType(Class<T> t, String attr) throws IllegalArgumentException {
        for (T arch : t.getEnumConstants()) {
            if (arch.toString().equals(attr)) {
                return of(arch);
            }
        }
        return empty();
    }

    /**
     * Inverse operation of makeArchetype():
     * {@link #makeArcheType(java.lang.Class, java.lang.String)}.
     *
     * @param <T> An enumerator class.
     * @param t An enumerator object.
     * @return The string representation of t.
     */
    public static <T extends Enum<?>> String printArchetype(T t) {
        return t.toString();
    }

    /**
     * Makes a PlayerCharacter. Example usage: "10;9;8;7;6;0,1,2" becomes new
     * PlayerCharacter(10,9,8,7,6, new Coords(0,1,2).
     *
     * @param field The string representation of the PlayerCharacter.
     * @return The parsed PlayerCharacter.
     * @see PlayerCharacter
     * @see BasicPlayerCharacter
     */
    public static PlayerCharacter makePlayerCharacter(String field) throws IllegalArgumentException {
        String[] attrs = field.split(";");
        int hp = parseInt(attrs[0]);
        int str = parseInt(attrs[1]);
        int per = parseInt(attrs[2]);
        int end = parseInt(attrs[3]);
        int agl = parseInt(attrs[4]);
        Coords coords = makeCoords(attrs[5]);
        int points = parseInt(attrs[6]);

        return new BasicPlayerCharacter(hp, str, per, end, agl, coords, DOWN, points);
    }

    /**
     * The inverse operation of makePlayerCharacter:
     * {@link #makePlayerCharacter(java.lang.String)}.
     *
     * @param pc The PlayerCharacter.
     * @return The TextFileGameLevels compatible string representation of pc.
     */
    public static String printPlayerCharacter(PlayerCharacter pc) {
        return pc.getHp() + ";" + pc.getStr() + ";" + pc.getPer() + ";" + pc.getEnd() + ";" + pc.getAgl() + ";" + printCoords(pc.getCoords()) + ";" + pc.getPoints();
    }

    /**
     * Accepts strings of the form (id);(coords) to generate a
     * NonPlayerCharacter.
     *
     * @param field The string based on which the NonPlayerCharacter is
     * generated.
     * @return Returns a NonPlayerCharacter, if one can be parsed from the given
     * string. Else returns empty.
     * @see NonPlayerCharacter
     */
    public static Optional<NonPlayerCharacter> makeNonPlayerCharacter(String field) throws IllegalArgumentException {
        String[] attrs = field.split(";");
        if (attrs.length < 2) {
            return empty();
        }
        Optional<NonPlayerCharacterArchetype> npca = makeArcheType(NonPlayerCharacterArchetype.class, attrs[0]);
        Coords coords = makeCoords(attrs[1]);

        if (npca.isPresent()) {
            return of(new HostileNonPlayerCharacter(npca.get(), coords, DOWN));
        }
        return empty();
    }

    /**
     * The inverse operation of makeNonPlayerCharacter:
     * {@link #makeNonPlayerCharacter(java.lang.String)}.
     *
     * @param npc NonPlayerCharacter to be converted.
     * @return Returns TextFileGameLevels compatible string representation.
     */
    public static String printNonPlayerCharacter(NonPlayerCharacter npc) {
        return npc.getId() + ";" + printCoords(npc.getCoords());
    }

    /**
     *
     * @param field The string representation of this LinkObject.
     * @return The parsed LinkObject.
     */
    public static Optional<LevelLink> makeLinkObject(String field) throws IllegalArgumentException {
        String[] attrs = field.split(";");
        if (attrs.length < 3) {
            return empty();
        }
        char id = attrs[0].charAt(0);
        Coords coords = makeCoords(attrs[1]);
        String address = attrs[2];
        return of(new LevelLink(id, coords, address));
    }

    public static String printLinkObject(LevelLink linkObject) {
        return linkObject.getId() + ";" + printCoords(linkObject.getCoords()) + ";" + linkObject.getLinkAddress();
    }

}
