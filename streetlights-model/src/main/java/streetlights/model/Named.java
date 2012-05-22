/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 NMCB B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

package streetlights.model;

/**
 * @author Marco Borst
 * @since 28/03/12
 */
public interface Named
{
  String getName();

  /**
   * Returns the {@code String} representation of this object including its name.  Implementers of this interface are required
   * to include the name property of this object in the String representation that an implementation of this method returns.
   * i.e. they are required to call back on {@link #getName()}.
   *
   * @return the {@code String} representation of this object including its name.
   */
  String toString();
}
